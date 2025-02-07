package org.example.spring_for_project.services;

import jakarta.servlet.http.HttpSession;
import org.example.spring_for_project.models.User;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final HttpSession session;

    public SessionService(HttpSession session) {
        this.session = session;
    }

    public void setCurrentUser(User user) {
        session.setAttribute("currentUser", user);
    }

    public User getCurrentUser() {
        return (User) session.getAttribute("currentUser");
    }

    public void logout() {
        session.invalidate();
    }
}

