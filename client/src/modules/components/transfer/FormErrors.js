import {Alert, Col, Row} from "react-bootstrap";

export default (props) => {
    const {formErrors = []} = props;
    return (<Row>
            <Col>
                {formErrors.map((text, i) => (<Alert key={`formError_${i}`} variant="danger">
                    {text}
                </Alert>))}
            </Col>
        </Row>
    );
}