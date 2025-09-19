FROM php:8-apache

# Enable Apache proxy modules
RUN a2enmod proxy proxy_http

# Copy the application code
COPY index.php /var/www/html/