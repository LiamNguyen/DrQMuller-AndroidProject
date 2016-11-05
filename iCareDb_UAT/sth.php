<?php
$server = "127.0.0.1";
$username = "longvh";
$password = "12345";
$dbname = "icaredb";


// Create connection
$con=mysqli_connect($server,$username,$password,$dbname);

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

mysqli_set_charset($con,"utf8");

// This SQL statement selects ALL from the table 'Locations'
$to = 'testingemail607@gmail.com';
$subject = "Sign Up | Verification";
$message = "

Tài khoản của bạn đã được tạo thành công.
Để hoàn tất đăng ký, bấm vào link dưới đây để kích hoạt tài khoản:
<a href=\"http://192.168.0.102/verify?email=syrisahihi@gmail.com&code=dc6a6489640ca02b0d42dabeb8e46bb7\">Link</a>

";
$header = "From: admin@yourwebsite.com" . "\r\n";
$header .= 'Content-type: text/html; charset=utf-8' . "\r\n";
echo mail($to, $subject, $message, $header);

// Close connections
mysqli_close($con);
?>