package view;

import javax.swing.*;
import javax.swing.text.*;

public class LimitedDocumentFilter extends DocumentFilter {

    private final int maxLength;

    public LimitedDocumentFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
        if ((fb.getDocument().getLength() + str.length()) <= maxLength) {
            super.insertString(fb, offset, str, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
        int totalLength = fb.getDocument().getLength() - length + str.length();
        if (totalLength <= maxLength) {
            super.replace(fb, offset, length, str, attrs);
        }
    }
}