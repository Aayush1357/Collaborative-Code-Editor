// src/main/resources/static/app.js
let stompClient = null;
const codeEditor = document.getElementById('codeEditor');
const messagesDiv = document.getElementById('messages');
const usernameInput = document.getElementById('username');
const roomIdInput = document.getElementById('roomId');

function joinRoom() {
    const username = usernameInput.value.trim();
    const roomId = roomIdInput.value.trim();

    if (!username || !roomId) {
        alert('Please enter a username and room ID');
        return;
    }

    // Connect to WebSocket endpoint
    const socket = new SockJS('/collab');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log('Connected to STOMP');

        // Subscribe to room-specific topic
        stompClient.subscribe('/topic/room/' + roomId, (message) => {
            const data = JSON.parse(message.body);
            if (data.event === 'codeUpdate') {
                codeEditor.value = data.code;
            } else if (data.event === 'userJoined' || data.event === 'userLeft') {
                messagesDiv.innerHTML += `<p>${data.message}</p>`;
                messagesDiv.scrollTop = messagesDiv.scrollHeight; // Auto-scroll to latest message
            }
        });

        // Send join room message
        stompClient.send('/app/joinRoom/' + roomId, {},
            JSON.stringify({ username: username }));
    }, (error) => {
        console.error('STOMP error:', error);
        messagesDiv.innerHTML += '<p>Connection failed. Please try again.</p>';
    });
}

// Send code updates on input

codeEditor.addEventListener('input', () => {
    if (stompClient && stompClient.connected) {
        const roomId = roomIdInput.value.trim();
        stompClient.send('/app/codeUpdate/' + roomId, {},
            JSON.stringify({ code: codeEditor.value }));
    }
});

// Handle disconnect
window.onbeforeunload = () => {
    if (stompClient && stompClient.connected) {
        stompClient.disconnect(() => {
            console.log('Disconnected from STOMP');
        });
    }
};