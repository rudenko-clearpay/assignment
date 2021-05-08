import {Alert, Col, Form} from "react-bootstrap";


export default (props) => {

    const {placeholder, onChange, fieldName, errors} = props;

    function getChangeHandler(fieldName) {
        return (e) => onChange(e, fieldName);
    }

    function getControlErrors(errors, fieldName) {
        return errors[fieldName].map((text, i) => (<Form.Control.Feedback key={`${fieldName}_e_${i}`} type="invalid">
            {text}
        </Form.Control.Feedback>));
    }

    return (
        <Col>
            <Form.Control placeholder={placeholder} onChange={getChangeHandler(fieldName)}
                          isInvalid={errors && errors[fieldName]} controlId={fieldName}/>
            {errors && errors[fieldName] && getControlErrors(errors, fieldName)}
        </Col>
    );
}