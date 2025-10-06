#!/bin/bash

# Debug: Show DATABASE_URL (masked for security)
echo "DATABASE_URL provided: ${DATABASE_URL:+***set***}"

# Convert Railway DATABASE_URL format to Spring Boot JDBC format
if [ ! -z "$DATABASE_URL" ]; then
    # Railway provides: postgresql://user:pass@host:port/db
    # Spring Boot needs: jdbc:postgresql://host:port/db
    
    echo "Converting DATABASE_URL format for Spring Boot..."
    
    # Extract the URL part after postgresql://
    URL_PART=${DATABASE_URL#postgresql://}
    
    # Extract user:password@host:port/database
    USER_PASS_HOST_PORT_DB=$URL_PART
    
    # Extract everything after the @ symbol (host:port/database)
    HOST_PORT_DB=${USER_PASS_HOST_PORT_DB#*@}
    
    # Create the JDBC URL
    SPRING_DATABASE_URL="jdbc:postgresql://${HOST_PORT_DB}"
    export DATABASE_URL="$SPRING_DATABASE_URL"
    
    echo "Converted DATABASE_URL to: $SPRING_DATABASE_URL"
else
    echo "No DATABASE_URL provided, using default configuration"
fi

# Start the application
exec java -jar app.jar