/*DOM OBJECTS*/
const usernameInput = document.getElementById("username");
const groupnameInput = document.getElementById("groupname");
const connectBTN = document.getElementById("connect");
const disconnectBTN = document.getElementById("disconnect");
const messageInput = document.getElementById("message");
const sendMessageBTN = document.getElementById("send");
const messagesBody = document.getElementById("messages");
/*DOM OBJECTS*/

/*GLOBAL VARIABLES*/
socket = null;
/*GLOBAL VARIABLES*/

/*Helpers*/
function clearEverything(){
    usernameInput.value = "";
    groupnameInput.value = "";
    messageInput.value = "";
    messagesBody.innerHTML = "";
}

function newMessage(message){
    const newRow = messagesBody.insertRow();
    const newCell = newRow.insertCell();
    const newText = document.createElement("div");
    newText.innerHTML = "<b>" + message.username + "</b> : " + message.message;
    newCell.appendChild(newText);
}

function toggleDOM(bool){

    usernameInput.disabled = bool;
    groupnameInput.disabled = bool;
    connectBTN.disabled = bool;

    disconnectBTN.disabled = !bool;
    messageInput.disabled = !bool;
    sendMessageBTN.disabled = !bool;

}
/*Helpers*/

/*EventListeners*/
connectBTN.addEventListener('click',function(e){
    toggleDOM(true);
    socketOperations();
    e.preventDefault();
});

disconnectBTN.addEventListener('click',function(e){
    clearEverything();
    toggleDOM(false);
    if(socket){
        socket.disconnect();
    }
    e.preventDefault();
});

sendMessageBTN.addEventListener('click',function(e){
    const jsonObject = {
        username:usernameInput.value,
        message:messageInput.value
    }
    socket.emit('messageEvent',jsonObject);
    e.preventDefault();
});
/*EventListeners*/

/*SocketOperations*/
function socketOperations(){
    socket = io('http://192.168.1.35:9092');

    socket.on('connect',function(){
        console.log('connected');
    });

    socket.on('messageBroadcast',function(data){
        newMessage(data);
    });

    socket.on('disconnect',function(){
        console.log('disconnected');
    });
}


/*SocketOperations*/