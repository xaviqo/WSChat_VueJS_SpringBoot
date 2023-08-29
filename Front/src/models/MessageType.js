const MessageType = Object.freeze({
    MESSAGE:{
        type: "MESSAGE"
    },
    SPAM: {
        type: "SPAM"
    },
    JOIN: {
        type: "JOIN"
    },
    LEAVE: {
        type: "LEAVE"
    },
    KICK: {
        type: "KICK"
    },
    BAN: {
        type: "BAN"
    },
    DELETE: {
        type: "DELETE"
    }
});

export default MessageType;