import { act, findByRole, findByTestId, findByText, fireEvent, getByDisplayValue, getByRole, getByTestId, getByText, queryByText, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import App from "../App";

describe('test', () => {
    test('test1', async () => {
        window.open = jest.fn();

        const { container } = render(
            <>
                <App></App>
            </>
        );
        // before click select component, virtual list not show the options
        expect(queryByText(container, 'label1')).toBeNull()
        expect(queryByText(container, 'label2')).toBeNull()

        const select = await findByRole(container, 'combobox')
        expect(select).toBeTruthy();

        act(() => {
            userEvent.click(select!);
        })
        // click triggers the shown of options
        expect(screen.findByTitle('label1')).toBeTruthy();
        expect(screen.findByTitle('label2')).toBeTruthy();

        const opt = await screen.findByTitle('label1')
        act(() => {
            fireEvent.click(opt);
        })

        console.log(select)
        expect(screen.getAllByText('label1').length).toBe(2)
        expect(screen.getAllByText('label2').length).toBe(1)
        expect(queryByText(container, 'label1')).toBeTruthy()
        console.log(queryByText(container, 'label1'));

        expect(queryByText(container, 'label2')).toBeNull()
    });
});