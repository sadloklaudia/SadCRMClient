package com.sad.sadcrm.form;

import com.sad.sadcrm.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static javax.swing.JOptionPane.*;

class ApplicationWindow extends JFrame {
    private final static String DEFAULT_TITLE = "SadCRM";
    private final String version;

    ApplicationWindow(String version) {
        this.version = version;
        setMinimumSize(new Dimension(1000, 800));
        setMaximumSize(new Dimension(1000, 800));
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        updateFormTitle(null);
    }

    void updateFormTitle(User user) {
        if (user == null) {
            setTitle(format("Klient SadCRM v%s", version));
        } else {
            setTitle(format("Klient SadCRM v%s (jako %s)", version, user.getType().getTitle()));
        }
    }

    MessageBoxBuilder messageBox(String message) {
        return new MessageBoxBuilder(message);
    }

    class MessageBoxBuilder {
        private final String message;
        private String title = DEFAULT_TITLE;
        private int messageType = JOptionPane.PLAIN_MESSAGE;
        private Options options = new Options();
        private String[] textOptions;

        MessageBoxBuilder(String message) {
            this.message = message;
        }

        MessageBoxBuilder title(String title) {
            this.title = title;
            return this;
        }

        MessageBoxBuilder type(int type) {
            this.messageType = type;
            return this;
        }

        void show() {
            showMessageDialog(ApplicationWindow.this, message, title, messageType);
        }

        private void performAsk() {
            messageType = JOptionPane.QUESTION_MESSAGE;
            int result = showOptionDialog(ApplicationWindow.this,
                    message, title, DEFAULT_OPTION, messageType, null, textOptions, textOptions[0]);
            this.options.runnables.getOrDefault(result, () -> {
            }).run();
        }

        Options options(String... textOptions) {
            this.textOptions = textOptions;
            return this.options;
        }

        class Options {
            private Map<Integer, Runnable> runnables = new HashMap<>();

            Options onYes(Runnable runnable) {
                runnables.put(YES_OPTION, runnable);
                return this;
            }

            public Options onNo(Runnable runnable) {
                runnables.put(NO_OPTION, runnable);
                return this;
            }

            public Options on(int option, Runnable runnable) {
                runnables.put(option, runnable);
                return this;
            }

            void ask() {
                performAsk();
            }
        }
    }
}
