import javax.swing.text.*;

public class AlphaSpaceFilter extends DocumentFilter 
{
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && string.matches("[a-zA-Z\\s]+")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && text.matches("[a-zA-Z\\s]*")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
