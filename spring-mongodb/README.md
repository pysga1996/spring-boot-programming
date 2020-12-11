Spring MongoDB demo app

Để chạy được cần set các biến môi trường sau:

1. Mongo DB config\
MONGO_CONNECTION_URL=connection string mongodb, có thể dùng cluster trên mongo atlas hoặc string connect trên local\
MONGO_DB=tên database

2. Mail config: mail dùng để gửi token kích hoạt tài khoản khi đăng ký user, tham khảo https://www.baeldung.com/spring-email \
MAIL_HOST\
MAIL_USERNAME\
MAIL_PASSWORD\
MAIL_TRANSPORT_PROTOCOL\
MAIL_SMTP_PORT\
MAIL_SMTP_AUTH\
MAIL_SMTP_STARTTLS_ENABLE

3. RSA key config cho spring oauth2 jwt, hiện tại project sử dụng JwtTokenStore, nếu muốn dùng access token mặc định thì cấu hình thành JDBC token store (token lưu trong database) hoặc InMemoryTokenStore (token lưu trên RAM)\
PRIVATE_KEY\
PUBLIC_KEY

4. Cấu hình để redirect khi click vào link activate account trong email\
BACKEND_HOST\
FRONTEND_HOST

các biến môi trường đặt trong các file đuôi .env trong folder environment_variables.
khi chạy bằng intellij có thể cài plugin để load các file .env: https://plugins.jetbrains.com/plugin/7861-envfile
