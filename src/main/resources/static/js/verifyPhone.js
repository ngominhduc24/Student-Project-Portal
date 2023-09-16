const inputs = document.querySelectorAll("input"),
    button = document.querySelector("button");
var code = '';

// iterate over all inputs
inputs.forEach((input, index1) => {
    input.addEventListener("keyup", (e) => {
        // This code gets the current input element and stores it in the currentInput variable
        // This code gets the next sibling element of the current input element and stores it in the nextInput variable
        // This code gets the previous sibling element of the current input element and stores it in the prevInput variable
        const currentInput = input,
            nextInput = input.nextElementSibling,
            prevInput = input.previousElementSibling;
        code += currentInput.value;
        // if the value has more than one character then clear it
        if (currentInput.value.length > 1) {
            currentInput.value = "";
            return;
        }
        // if the next input is disabled and the current value is not empty
        //  enable the next input and focus on it
        if (nextInput && nextInput.hasAttribute("disabled") && currentInput.value !== "") {
            nextInput.removeAttribute("disabled");
            nextInput.focus();
        }

        // if the backspace key is pressed
        if (e.key === "Backspace") {
            // iterate over all inputs again
            inputs.forEach((input, index2) => {
                // if the index1 of the current input is less than or equal to the index2 of the input in the outer loop
                // and the previous element exists, set the disabled attribute on the input and focus on the previous element
                if (index1 <= index2 && prevInput) {
                    input.setAttribute("disabled", true);
                    input.value = "";
                    prevInput.focus();
                }
            });
        }
        //if the fourth input( which index number is 3) is not empty and has not disable attribute then
        //add active class if not then remove the active class.
        if (!inputs[5].disabled && inputs[5].value !== "") {
            button.classList.add("active");
            alert(code)
            return;
        }
        button.classList.remove("active");
    });
});


// Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyCM-VrqHZKLBLhJzlDudOKJGUGul4W7sGk",
    authDomain: "swp391-3fcbc.firebaseapp.com",
    projectId: "swp391-3fcbc",
    storageBucket: "swp391-3fcbc.appspot.com",
    messagingSenderId: "691277329952",
    appId: "1:691277329952:web:1e280a9bab40c640de2e45",
    measurementId: "G-MVNE99TDXQ"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);

firebase.auth().settings.appVerificationDisabledForTesting = true;
var appVerifier = new firebase.auth.RecaptchaVerifier('recaptcha-container');
let coderesult;

// function for send OTP
function phoneAuth() {
    var number = document.getElementById('number').value;
    firebase.auth().signInWithPhoneNumber(number, appVerifier).then(function (confirmationResult) {
        window.confirmationResult = confirmationResult;
        coderesult = confirmationResult;
    }).catch(function (error) {
        alert(error.message);
    });
}

const verify_btn = document.getElementById('verify');
verify_btn.addEventListener('click', () => {
    const token_sender = document.getElementById("token_holder").textContent;
    window.confirmationResult.confirm(code).then(function () {
        location.href = token_sender;
    }).catch(function () {
        alert('OTP Not correct');
    })
})


//focus the first input which index is 0 on window load
window.addEventListener("load", () => inputs[0].focus());
window.addEventListener("load", phoneAuth);