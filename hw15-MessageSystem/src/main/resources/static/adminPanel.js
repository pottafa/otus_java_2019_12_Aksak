var stompClient = null;

function connect() {
    var socket = new SockJS('/admin');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/newUser', function (lala)
        { showUsers(JSON.parse(lala.body).age, JSON.parse(lala.body).name, JSON.parse(lala.body).login, JSON.parse(lala.body).password);
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
