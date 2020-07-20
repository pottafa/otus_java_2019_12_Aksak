var stompClient = null;

function connect() {
    var socket = new SockJS('/admin');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/newUser', function (user)
        { showUsers(JSON.parse(user.body).age, JSON.parse(user.body).name, JSON.parse(user.body).login, JSON.parse(user.body).password);
        });

        stompClient.subscribe('/topic/getUsers', function (users)
                {
                showUsers(44, "sfsgfdwe", "aleeee", "safdhsug");
                 $.each(users, function(i, f) {
                           showUsers(JSON.parse(f.body).age, JSON.parse(f.body).name, JSON.parse(f.body).login, JSON.parse(f.body).password);
                          });
                });
    });
}

function createUser() {
    stompClient.send("/app/new_user", {}, JSON.stringify({'login': $("#login").val(), 'password': $("#password").val(),'name': $("#name").val(), 'age': $("#age").val(),}));
}
function showUsers(age, name, login, password) {
    $("#users").append("<tr><td>" + login + "</td> + <td>" + password + "</td> + <td>" + name + "</td>+ <td>" + age + "</td></tr>");
}

$(function () {
  $("form").on('submit', (event) => {
    event.preventDefault();
  });
  $("#send").click(createUser);
  connect();
});
