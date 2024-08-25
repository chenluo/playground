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
        // const select= container.querySelector('#single-select-div .ant-select')
        // expect(select).toBeTruthy();
        expect(queryByText(container, 'label')).toBeNull()

        const select = await findByRole(container, 'combobox')
        // const select = getByRole(container, 'combobox')
        expect(select).toBeTruthy();

        userEvent.click(select!);
        expect(screen.findByTitle('label')).toBeTruthy();

        const opt = await screen.findByTitle('label')
        fireEvent.click(opt);

        console.log(select)
        expect(screen.getAllByText('label')).toBeTruthy()
        expect(queryByText(container, 'label')).toBeTruthy()

    });
});