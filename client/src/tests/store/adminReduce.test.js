import {setIsAdmin} from "../../store/actions";
import {adminReducer} from "../../store/reducers/adminReducer";


describe('Admin reducer test', () => {
    it('setIsAdmin action changes isAdmin value', () => {
        expect(adminReducer(undefined, {})).toStrictEqual({isAdmin: false});
    })

    it('setIsAdmin action changes isAdmin value', () => {
        expect(adminReducer(undefined, setIsAdmin(true))).toStrictEqual({isAdmin: true});
        expect(adminReducer(undefined, setIsAdmin(false))).toStrictEqual({isAdmin: false});
    })
});

