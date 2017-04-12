package com.sad.sadcrm.form;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static javax.swing.JOptionPane.*;

public class ApplicationWindow extends JFrame {
    private final static String DEFAULT_TITLE = "SadCRM";

    ApplicationWindow(String version) {
        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(format("Klient SadCRM v%s", version));
    }

    MessageBoxBuilder messageBox(String message) {
        return new MessageBoxBuilder(message);
    }

    private ApplicationWindow window() {
        return this;
    }

    class MessageBoxBuilder {
        private final String message;
        private String title = DEFAULT_TITLE;
        private int messageType = JOptionPane.PLAIN_MESSAGE;
        private Options options = new Options();
        private String[] textOptions;

        public MessageBoxBuilder(String message) {
            this.message = message;
        }

        public MessageBoxBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MessageBoxBuilder type(int type) {
            this.messageType = type;
            return this;
        }

        public void show() {
            showMessageDialog(window(), message, title, messageType);
        }

        private void performAsk() {
            messageType = JOptionPane.QUESTION_MESSAGE;
            int result = showOptionDialog(window(), message, title, DEFAULT_OPTION, messageType, null, textOptions, null);
            this.options.runnables.getOrDefault(result, () -> {
            }).run();
        }

        public Options options(String... textOptions) {
            this.textOptions = textOptions;
            return this.options;
        }

        class Options {
            private Map<Integer, Runnable> runnables = new HashMap<>();

            public Options onYes(Runnable runnable) {
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

            public void ask() {
                performAsk();
            }
        }
    }
}
