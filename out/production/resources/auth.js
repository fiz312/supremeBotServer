app.controller("authController", function($scope, $location, $http){
  const submit = document.getElementById("submit");
  submit.addEventListener("click", function() {
    let username = document.getElementById("username").value;
    let pass = document.getElementById("password").value;
    let credentials = "Basic " + btoa(username + ":" + pass);
    $http.get("http://localhost:8080/auth", {
      headers : {"Authorization" : credentials}
    })
    .then(function(response) {
      localStorage.setItem("token", response.data);
      authenticated = true;
      $location.path("/");
    });
  });
});
