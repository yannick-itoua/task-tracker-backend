#!/bin/bash

# Convert Railway DATABASE_URL format to Spring Boot format
if [ ! -z "$DATABASE_URL" ]; then
    # Railway provides: postgresql://user:pass@host:port/db
    # Spring Boot needs: jdbc:postgresql://host:port/db for SPRING_DATASOURCE_URL
    # And separate PGUSER and PGPASSWORD
    
    # Extract components from DATABASE_URL
    # Example: postgresql://postgres:password@postgres.railway.internal:5432/railway
    SPRING_DATASOURCE_URL=$(echo $DATABASE_URL | sed 's|postgresql://|jdbc:postgresql://|')
    export SPRING_DATASOURCE_URL
    
    echo "Converted DATABASE_URL to SPRING_DATASOURCE_URL: $SPRING_DATASOURCE_URL"
fi

# Start the application
exec java -jar app.jar