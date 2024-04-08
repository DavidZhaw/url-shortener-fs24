# URL Shortener

The URL Shortener is a web application that allows users to shorten long URLs into shorter, more manageable links. 
It provides a convenient way to share links on social media platforms, emails, or any other medium where long URLs may be cumbersome. 

## Features

- Shorten URLs: Users can enter a long URL and the application will generate a unique, shortened URL.
- Manage URLs: delete, shoare or open own URLs (User), Manage all URLs (Admin)
- Redirects: When a shortened URL is accessed, the application redirects the user to the original long URL.

## Sequence Diagram

![Sequence Diagram](doc/figures/sequence-diagram.drawio.svg)

## Set-up

To run this application (on localhost):
- In src\main\resources\application.properties:
	- set MongoDB connection String as spring.data.mongodb.uri 
	- set auth0-Domain as auth0.domain 
- In urlshortener-frontend\src\auth.config.js:
	- set auth0-Domain as  
	- set auth0-Client ID as auth0_client_id
- In urlshortener-frontend
	- run >npm install
	- run >npm run watch
- Start the spring boot application

## License

This project is licensed under the [MIT License](LICENSE).


