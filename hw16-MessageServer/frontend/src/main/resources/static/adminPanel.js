var stompClient = null;

function connect() {
    var socket = new SockJS('/admin');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/newUser', function (user)
        { showUsers(JSON.parse(user.body).age, JSON.parse(user.body).name, JSON.parse(user.body).login, JSON.parse(user.body).password);
        });

        stompClient.subscribe('/app/getUsers', function (users)
                {
                });
         stompClient.subscribe('/topic/getUsers', function (users)
                         {
                         var users_list = JSON.parse(users.body);
                         showUsers(44, "sfsgfdwe", "aleeee", "safdhsug");
                          $.each(users_list, function(i, user) {
                                    showUsers(user.age, user.name, user.login, user.password);
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
