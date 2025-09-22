FROM php:8.2-fpm-alpine

# Install system dependencies
RUN apk --no-cache add \
    librdkafka-dev \
    git \
    zip \
    unzip

# Install rdkafka PHP extension
RUN pecl install rdkafka && docker-php-ext-enable rdkafka

# Install Composer
RUN php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');" && \
    php composer-setup.php --install-dir=/usr/local/bin --filename=composer && \
    php -r "unlink('composer-setup.php');"

# Set working directory
WORKDIR /var/www/html

# Copy composer files and install dependencies
COPY composer.json ./
RUN composer install --no-interaction --no-plugins --no-scripts --prefer-dist

# Copy application code
COPY . .

# Expose port 9000 for PHP-FPM
EXPOSE 9000
