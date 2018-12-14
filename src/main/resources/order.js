app.controller("orderController", function($scope, $location, $http){
    $http.get("http://localhost:8080/api/orders/order/", {
      headers : { "Auth" : "Bearer " + localStorage.getItem('token')}
    }).then(function(response) {
      var data = response.data;
      $scope.name = data['orderConfiguration']['clientDetails']['name'];
      $scope.email = data['orderConfiguration']['clientDetails']['email'];
      $scope.telephoneNumber = data['orderConfiguration']['clientDetails']['telephoneNumber'];

      $scope.address1 = data['orderConfiguration']['address']['address1'];
      $scope.address2 = data['orderConfiguration']['address']['address2'];
      $scope.address3 = data['orderConfiguration']['address']['address3'];
      $scope.city = data['orderConfiguration']['address']['city'];
      $scope.postcode = data['orderConfiguration']['address']['postcode'];
      document.getElementById(data['orderConfiguration']['address']['country']).selected = true;

      document.getElementById(data['orderConfiguration']['cardDetails']['buyType']).selected = true;
      $scope.cardNumber = data['orderConfiguration']['cardDetails']['cardNumber'];
      $scope.cw = data['orderConfiguration']['cardDetails']['cw'];
      $scope.expMonth = data['orderConfiguration']['cardDetails']['expDateMonth'];
      $scope.expYear = data['orderConfiguration']['cardDetails']['expDateYear'];

      $scope.itemName = data['item']['name'];
      $scope.image = data['item']['image'];
      $scope.category = data['item']['category'];

      document.getElementById('execute').checked = data['execute']==true ? "checked" : "";
    });

    const update = document.getElementById('update');
    update.addEventListener('click', function() {
      var name = document.getElementById('name').value;
      var email = document.getElementById('email').value;
      var telephoneNumber = document.getElementById('telephoneNumber').value;

      var address1 = document.getElementById('address1').value;
      var address2 = document.getElementById('address2').value;
      var address3 = document.getElementById('address3').value;
      var city = document.getElementById('city').value;
      var postcode = document.getElementById('postcode').value;
      var country = document.getElementById('country').value;

      var buyType = document.getElementById('buyType').value;
      var cardNumber = document.getElementById('cardNumber').value;
      var cw = document.getElementById('cw').value;
      var expMonth = document.getElementById('expMonth').value;
      var expYear = document.getElementById('expYear').value;

      var execute = document.getElementById('execute').checked;

      var itemName = document.getElementById('itemName').value;
      var image = document.getElementById('image').value;
      var category = document.getElementById('category').value;

      var data = JSON.stringify({
        "orderConfiguration" : {
          "clientDetails" : {
            "name" : name,
            "email" : email,
            "telephoneNumber" : telephoneNumber
          },
          "address" : {
            "address1": address1,
			      "address2": address2,
			      "address3": address3,
			      "city": city,
			      "postcode": postcode,
			      "country": country
          },
          "cardDetails": {
			      "buyType": buyType,
			      "cardNumber": cardNumber,
			      "cw": cw,
			      "expDateMonth": expMonth,
			      "expDateYear": expYear
          },
        },
        "execute": execute,
        'item' : {
          'name' : itemName,
          'image' : image,
          'category' : category
        }
      });
      $http.post("http://localhost:8080/api/orders/order/", data, {
        headers : { "Auth" : "Bearer " + localStorage.getItem('token')}
      }).then(function() {
        $location.path("/order");
      });
    });
});
