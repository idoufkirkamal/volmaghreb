# Render Deployment Configuration Guide

## Environment Variables to Set in Render Dashboard

When deploying to Render, you need to set these environment variables in your service settings:

### Required Environment Variables:

1. **SPRING_PROFILES_ACTIVE**
   - Value: `prod`
   - This activates the production configuration

2. **SPRING_DATASOURCE_URL**
   - Value: `jdbc:mysql://YOUR_AIVEN_HOST:PORT/defaultdb?useSSL=true&requireSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true`
   - This is your Aiven MySQL connection string with SSL settings
   - Replace YOUR_AIVEN_HOST:PORT with your actual Aiven connection details

3. **DB_USERNAME**
   - Value: Your Aiven database username (e.g., `avnadmin`)

4. **DB_PASSWORD**
   - Value: Your Aiven database password
   - ⚠️ **IMPORTANT**: Get this from your Aiven dashboard, do NOT commit passwords to Git!

5. **PORT** (optional)
   - Render automatically sets this, but you can specify: `8080`

### Optional Environment Variables:

6. **ADMIN_USERNAME**
   - Value: `admin` (or your preferred admin username)

7. **ADMIN_PASSWORD**
   - Value: your preferred admin password (or let Render generate one)

## Steps to Deploy on Render:

1. Connect your GitHub repository to Render
2. Create a new Web Service
3. Set the following:
   - **Environment**: Docker
   - **Dockerfile Path**: `./Dockerfile`
   - **Build Command**: (leave empty, Docker will handle it)
   - **Start Command**: (leave empty, Dockerfile ENTRYPOINT will handle it)

4. Add the environment variables listed above in the Environment section

5. Deploy!

## Health Check Endpoint:

Once deployed, you can check if your app is working by visiting:
`https://your-app-name.onrender.com/volmaghreb/health`

This will show you the database connection status and active profile.

## Local Testing:

To test the production configuration locally, use:
```bash
docker run -e SPRING_PROFILES_ACTIVE=prod -e DB_USERNAME=YOUR_USERNAME -e DB_PASSWORD=YOUR_PASSWORD -e SPRING_DATASOURCE_URL="jdbc:mysql://YOUR_AIVEN_HOST:PORT/defaultdb?useSSL=true&requireSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true" -p 8080:8080 volmaghreb-app
```

Replace YOUR_USERNAME, YOUR_PASSWORD, and YOUR_AIVEN_HOST:PORT with your actual Aiven credentials.

## Notes:

- The app will use the default profile (application.properties) when running locally without SPRING_PROFILES_ACTIVE
- The prod profile (application-prod.properties) is used when SPRING_PROFILES_ACTIVE=prod
- The configuration supports both SPRING_DATASOURCE_URL and DATABASE_URL for flexibility
