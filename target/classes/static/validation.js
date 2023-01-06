function validateLoginForm() {
    var login = document.getElementById("login");
    var password = document.getElementById("password");

    var result = true;

    var regex = /^.{5,}$/;

    if(!regex.test(login.value)) {
        result = false;
        login.style.background = "#cd5c5c";
    } else {
        login.style.background = "#ffffff";
    }

    if(password.value.length < 5) {
        result = false;
        password.style.background = "#cd5c5c";
    } else {
        password.style.background = "#ffffff";
    }

    return result;
}

function validateRegisterForm() {
    var login = document.getElementById("login");
    var password = document.getElementById("password");
    var password2 = document.getElementById("password2");

    var result = true;

        if(login.value.length < 5) {
            result = false;
            login.style.background = "#cd5c5c";
        } else {
            login.style.background = "#ffffff";
        }

        if(password.value.length < 5) {
            result = false;
            password.style.background = "#cd5c5c";
        } else {
            password.style.background = "#ffffff";
        }

        if(password2.value.length < 5 || password2.value !== password.value) {
            result = false;
            password2.style.background = "#cd5c5c";
        } else {
            password2.style.background = "#ffffff";
        }

        return result;
}