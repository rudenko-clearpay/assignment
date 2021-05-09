import {setIsLoggedIn} from "../../store/actions";
import {loginReducer} from "../../store/reducers/loginReducer";


describe('Login reducer test', () => {
    it('setIsLoggedIn action changes isLoggedIn value', () => {
        expect(loginReducer(undefined, {})).toStrictEqual({isLoggedIn: false});
    })

    it('setIsLoggedIn action changes isLoggedIn value', () => {
        expect(loginReducer(undefined, setIsLoggedIn(true))).toStrictEqual({isLoggedIn: true});
        expect(loginReducer(undefined, setIsLoggedIn(false))).toStrictEqual({isLoggedIn: false});
    })
});

