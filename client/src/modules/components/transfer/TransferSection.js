import {Card, Col, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {useState} from "react";
import {transfer} from "../../../api/transfers";
import {useDispatch} from "react-redux";
import {updateUsers} from "../../../store/actions";
import FieldControl from "./FieldControl";
import FormErrors from "./FormErrors";


const TransferSection = (props) => {
    const {user, originWallet} = props;
    const [recipientWalletId, setRecipientWalletId] = useState();
    const [amount, setAmount] = useState();
    const [errors, setErrors] = useState({});
    const dispatch = useDispatch();

    const fieldsMapping = {
        amount: setAmount,
        recipientWalletId: setRecipientWalletId
    };

    const onSubmit = async () => {
        const transaction = {
            senderId: user.id,
            senderWalletId: originWallet.id,
            recipientWalletId: recipientWalletId,
            amount: amount
        }

        const response = await transfer(transaction);
        if (response.status === 200) {
            dispatch(updateUsers(response))
        } else {
            debugger
            setErrors(response.data.errors);
        }
    }

    const onFieldChange = (e, fieldName) => {
        errors[fieldName] = null;
        setErrors(errors);
        fieldsMapping[fieldName](e.target.value);
    }

    return (<Card>
            <Card.Header>Transfer</Card.Header>
            <Card.Body>
                <Form noValidate>
                    <Form.Row>
                        <Col>
                            <Form.Label>Wallet ID</Form.Label>
                        </Col>
                        <Col>
                            <Form.Label>Amount</Form.Label>
                        </Col>
                        <Col>
                            <Form.Label/>
                        </Col>
                    </Form.Row>
                    <Form.Row>
                        <FieldControl placeholder="Wallet ID" onChange={onFieldChange} fieldName="recipientWalletId"
                                      errors={errors}/>
                        <FieldControl placeholder="0.0" onChange={onFieldChange} fieldName="amount" errors={errors}/>
                        <Col>
                            <Button variant="primary" onClick={onSubmit}>
                                Submit
                            </Button>
                        </Col>
                    </Form.Row>
                </Form>
                <FormErrors formErrors={errors.formErrors}/>
            </Card.Body>
        </Card>
    );
}

export default TransferSection;