
function sendFriendsRequest(given_user_id) {

    //Create "add to friends" request
    var friends_req = new XMLHttpRequest();

    //Define callback function
    friends_req.onreadystatechange = function() {
        
        //Check response status
        if (this.readyState == 4 && this.status == 200) {
            //Change friends button title.
            changeBtnTitle();
        }

    };

    //Create json
    var json_data = { user_id: given_user_id};

    //Open connections with "FriendsRequest" controller
    friends_req.open("POST", "/addToFriend", true);

    //Set content type
    friends_req.setRequestHeader("Content-Type", "application/json");

    //Send request
    friends_req.send(JSON.stringify(json_data));

}

function changeBtnTitle() {
    
    //Get friends button
    var btn = document.getElementById("friends_btn");
    
    //Disable and change title on it
    btn.disabled = true;
    btn.value = "You sent a request";

}

