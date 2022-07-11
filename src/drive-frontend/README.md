## Front End Layer

The frontend runs on `Vue 3` and `Vuetify 3 Beta`.

### Project setup
Make sure you have Node.js installed. Then, while inside the current directory, run

`npm install`

### Development
For compiling the sources and having the hot-reload feature while the app is still running, run

`npm run serve`

and go to the `localhost:8080` URL as defined in `.env.development` file. (if another service is running on port 8080, the frontend will run on the next available port, i.e. 8081)

For another environment, i.e. production, create a `.env.production` file with the same structure, set the correct IP and run

`npm run serve -- --mode production`

### Compiling and minifying sources for production
Run `npm run build`

### Linting and fixing files
Run `npm run lint`