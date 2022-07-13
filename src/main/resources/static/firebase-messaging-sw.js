 // Give the service worker access to Firebase Messaging.
 // Note that you can only use Firebase Messaging here. Other Firebase libraries
 // are not available in the service worker.
 importScripts('https://www.gstatic.com/firebasejs/9.8.3/firebase-app-compat.js');
 importScripts('https://www.gstatic.com/firebasejs/9.8.3/firebase-messaging-compat.js');
 // Initialize the Firebase app in the service worker by passing in
 // your app's Firebase config object.
 // https://firebase.google.com/docs/web/setup#config-object
 firebase.initializeApp({
    apiKey: "<<apiKey>>",
    authDomain: "<<authDomain>>",
    projectId: "<<projectId>>",
    storageBucket: "<<storageBucket>>",
    messagingSenderId: "<<messagingSenderId>>",
    appId: "<<appId>>",
    measurementId: "<<measurementId>>"
 });
 // Retrieve an instance of Firebase Messaging so that it can handle background
 // messages.
 const messaging = firebase.messaging();