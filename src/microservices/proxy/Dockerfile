FROM php:8-nginx

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy the application code
COPY index.php /var/www/html/

# Expose port 80
EXPOSE 80