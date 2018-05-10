import * as firebase from 'firebase';

var config = {
    apiKey: "AIzaSyC0TKGNcgphoQ0Kn2WnLZFPFLnlEwFXF2g",
    authDomain: "trding-8415c.firebaseapp.com",
    databaseURL: "https://trding-8415c.firebaseio.com",
    projectId: "trding-8415c",
    storageBucket: "trding-8415c.appspot.com",
    messagingSenderId: "9726496540"
};

 const firebaseApp  = firebase.initializeApp(config);

export default firebaseApp;
