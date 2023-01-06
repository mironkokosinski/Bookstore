function addToBasket(id) {
    $.ajax({
        url: "http://localhost:8080/api/basket/" + id,
        method: "GET"
    })
    .done(wynik => {

    });
}


function ajax() {

    var book = {
        id: 1,
        title: "Jakas ksiazka 1",
        author: "Janusz 1",
        description: "jakis opis",
        isbn: "123-123-123",
        price: 345.43,
        quantity: 8
    }

    $.ajax({
        url: "http://localhost:8080/api/book/5",
        method: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(book)
    })
    .done(response => {
        console.log(response.id);
        console.log(response.title);
        console.log(response.author);
        console.log(response.price);
        console.log(response.quantity);
    });
}