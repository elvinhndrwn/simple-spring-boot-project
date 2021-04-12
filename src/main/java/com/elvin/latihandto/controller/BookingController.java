package com.elvin.latihandto.controller;

import com.elvin.latihandto.config.Email;
import com.elvin.latihandto.dto.BookingDTO;
import com.elvin.latihandto.exception.ResourceNotFoundException;
import com.elvin.latihandto.mapper.BookingMapper;
import com.elvin.latihandto.model.Booking;
import com.elvin.latihandto.model.Feedback;
import com.elvin.latihandto.repository.BookingRepository;
import com.elvin.latihandto.response.BookingResponse;
import com.elvin.latihandto.service.BookingService;
import com.elvin.latihandto.service.GmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.validation.ValidationException;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GmailSenderService gmailSenderService;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Add Email konfig
     */

    private Email emailConfig;

    private SimpleMailMessage message;

    public BookingController(Email emailConfig){
        this.emailConfig =emailConfig;
    }

    @PostMapping(path = "/send")
    public ResponseEntity<?> send(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResourceNotFoundException("Maaf format tidak valid");
        }

        /**
         * Add mail sender / pengirim
         */

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());


        /**
         * Creat a Email
         */

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("elvinhndrwn@gmail.com");
        mailMessage.setSubject("Order produk");
        mailMessage.setFrom(bookingDTO.getEmail());
        mailMessage.setText(bookingDTO.getMsg());

        /**
         * Send the email
         */

        mailSender.send(mailMessage);

        /**
         * Save to database
         */
        Booking booking = BookingMapper.DtoToEntity(bookingDTO);
        Booking addBooking = bookingService.save(booking);

        /**
         * Find product name
         */


        /**
         * Response Config
         */
        BookingResponse response = new BookingResponse();
        response.setName(bookingDTO.getName());
        response.setFrom(bookingDTO.getEmail());
        response.setProductId(bookingDTO.getProductId());
        response.setProductName(String.valueOf(bookingService.getProductName(bookingDTO.getProductId())));
        response.setQty(bookingDTO.getQty());
        response.setStatus("Pending");
        response.setBooking_msg(bookingDTO.getMsg());
        response.setResponse_msg("Email Sent!");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ACCEPT REQUEST BOOKING
    @PutMapping(path = "/acc")
    public ResponseEntity<Booking> acc(@RequestParam int id, @RequestBody Booking accBooking){
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking order not found"));

        bookingRepository.save(accBooking);

        try {
            gmailSenderService.sendEmailWithAttachment(
                    "haqshudul@gmail.com",
                    "Order Accepted",
                    "Thank you for your order!",
                    "D:\\tutorials.xlsx");

        }catch (Exception e){
            throw new ResourceNotFoundException("404");
        }



        return new ResponseEntity<>(accBooking, HttpStatus.OK);
    }

    /**
     * Test send email with gmail
     */

    @PostMapping(path = "/gmail")
    public ResponseEntity<?> sendGmail(@RequestBody BookingDTO bookingDTO){

        /**
         * Save to database
         */

        Integer stockNow = bookingService.getProductName(bookingDTO.getProductId()).get(0).getStock();
        String namaProduk = String.valueOf(bookingService.getProductName(bookingDTO.getProductId()).get(0).getProductName());

        /**
         * Cek Stock dalam database
         */
        if(stockNow > bookingDTO.getQty()) {

            Booking booking = BookingMapper.DtoToEntity(bookingDTO);
            Booking addBooking = bookingService.save(booking);

            gmailSenderService.sendSimpleEmail(
                    bookingDTO.getEmail(),
                    "ORDER",
                    "==== Data Pembelian ====\n" +
                            "Nama   : " + bookingDTO.getName() + "\n" +
                            "Email  : " + bookingDTO.getEmail() + "\n" +
                            "Produk : " + namaProduk + "\n" +
                            "Jumlah Beli : " + bookingDTO.getQty() + "\n" +
                            "Notes  :" + bookingDTO.getMsg() + "\n" +
                            "\n" +
                            "Terimakasih telah membeli di Toko kami :)");


            BookingResponse response = new BookingResponse();

            response.setName(bookingDTO.getName());
            response.setFrom(bookingDTO.getEmail());
            response.setProductId(bookingDTO.getProductId());
            response.setProductName(namaProduk);
            response.setQty(bookingDTO.getQty());
            response.setStatus("Pending");
            response.setBooking_msg(bookingDTO.getMsg());
            response.setResponse_msg("Email Sent!");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }else{
            BookingResponse response = new BookingResponse();

            response.setName(bookingDTO.getName());
            response.setFrom(bookingDTO.getEmail());
            response.setProductId(bookingDTO.getProductId());
            response.setProductName(namaProduk);
            response.setQty(bookingDTO.getQty());
            response.setStatus("Failed");
            response.setBooking_msg(bookingDTO.getMsg());
            response.setResponse_msg("Maaf, stock tidak mencukupi untuk pembelian produk " + namaProduk);

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }

    }


    @PostMapping(path = "/gmailWithAttch")
    public ResponseEntity<String> gmailWithAttch() throws MessagingException{
        gmailSenderService.sendEmailWithAttachment(
                "haqshudul@gmail.com",
                "Test#1",
                "this my attachment",
                "D:\\tutorials.xlsx");

        return ResponseEntity.ok("Email with attachment has been send");
    }

    /**
     * Custom file upload
     * @param multipartFile
     * @return
     * @throws MessagingException
     */
    @PostMapping(path = "/sendFile")
    public ResponseEntity<String> sendFile(@RequestParam("file") MultipartFile multipartFile) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("goodvibesyk@gmail.com");
        mimeMessageHelper.setTo("elvinhndrwn@gmail.com");
        mimeMessageHelper.setSubject("Subjek");
        mimeMessageHelper.setText("Test");
        mimeMessageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);

        mailSender.send(mimeMessage);


        return ResponseEntity.ok("Email with attachment has been send");
    }
}
