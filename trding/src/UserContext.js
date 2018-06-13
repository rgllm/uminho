import React from 'react';

export const UserContext = React.createContext({
    user: {logged: false,
    data: undefined,},
    setUser : () => {}
});