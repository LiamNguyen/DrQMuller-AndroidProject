<?php
    require 'PHPMailer/PHPMailerAutoload.php';
    
    $EMAIL = $_POST['email'];
    
    $mail = new PHPMailer;

    //$mail->SMTPDebug = 3;                               // Enable verbose debug output

    $mail->isSMTP();                                      // Set mailer to use SMTP
    $mail->Host = 'smtp.gmail.com';  // Specify main and backup SMTP servers
    $mail->SMTPAuth = true;                               // Enable SMTP authentication
    $mail->Username = 'testingemail607@gmail.com';                 // SMTP username
    $mail->Password = 'adminlongdoptrai';                           // SMTP password
    $mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
    $mail->Port = 587;                                    // TCP port to connect to

    $mail->setFrom('from@example.com', 'iCare Service');
    $mail->addAddress($EMAIL, 'Me');     // Add a recipient
    //$mail->addAddress('ellen@example.com');               // Name is optional
    //$mail->addReplyTo('info@example.com', 'Information');
    //$mail->addCC('cc@example.com');
    //$mail->addBCC('bcc@example.com');

    //$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
    //$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
    $mail->isHTML(true);                                  // Set email format to HTML

    $mail->Subject = 'Xui Vui Lòng Xác Nhận Tài Khoản';
    $mail->Body    = '
    <!DOCTYPE html>
    <html>
    <head>
    </head>
    <body>
    <a href="http://192.168.0.106/verify?">Confirm Link</a>
    </body>
    </html>
    ';
    $mail->AltBody = 'This is the body in plain text for non-HTML mail clients';

    if(!$mail->send()) {
        echo json_encode(array("Send_Email" => "Message could not be sent", "ERROR" => $mail->ErrorInfo));
    } else {
        echo json_encode(array("Send_Email" => "Message has been sent"));
    }
?>