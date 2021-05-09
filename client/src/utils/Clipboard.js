class Clipboard {

    static async copyTextToClipboard(text) {
        if (!navigator.clipboard) {
            Clipboard.fallbackCopyTextToClipboard(text);
            return;
        }
        return navigator.clipboard.writeText(text);
    }

    static fallbackCopyTextToClipboard(text) {
        const textArea = document.createElement('textarea');
        textArea.value = text;

        // Avoid scrolling to bottom
        textArea.style.top = '0';
        textArea.style.left = '0';
        textArea.style.position = 'fixed';

        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();

        document.execCommand('copy');
        document.body.removeChild(textArea);
    }
}

export default Clipboard;