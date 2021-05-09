import Clipboard from "../../utils/Clipboard";

describe('Copying to the clipboard', () => {
    const expectedClipboardText = 'someText';
    it('copyTextToClipboard', async () => {
        const clipboard = {
            writeText: jest.fn(),
        };
        navigator.clipboard = clipboard;
        await Clipboard.copyTextToClipboard(expectedClipboardText);
        expect(clipboard.writeText).toHaveBeenCalledWith(expectedClipboardText);
    });

    it('copyTextToClipboard in older browsers', () => {
        navigator.clipboard = undefined;
        document.execCommand = jest.fn();
        Clipboard.copyTextToClipboard(expectedClipboardText);
        expect(document.execCommand).toHaveBeenCalledWith('copy');
    });
});