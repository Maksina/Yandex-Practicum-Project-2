FROM php:8.2-nginx

# Install system dependencies for rdkafka
RUN apt-get update && apt-get install -y \
    librdkafka-dev \
    git \
    zip \
    unzip

# Install rdkafka PHP extension
RUN pecl install rdkafka && docker-php-ext-enable rdkafka

# Install Composer
RUN php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');" && \
    php composer-setup.php --install-dir=/usr/bin --filename=composer && \
    php -r "unlink('composer-setup.php');"

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Set working directory
WORKDIR /var/www/html

# Copy composer files and install dependencies
COPY composer.json ./
RUN composer install --no-interaction --no-plugins --no-scripts --prefer-dist

# Copy application code
COPY .. .

# Expose port 8082
EXPOSE 8082
