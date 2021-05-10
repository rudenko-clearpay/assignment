import {Card, Col, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {useEffect, useState} from "react";
import {transfer} from "../../../api/transfers";
import {useDispatch} from "react-redux";
import {updateUsers} from "../../../store/actions";
import FieldControl from "./FieldControl";
import FormErrors from "./FormErrors";


const TransferSection = (props) => {
    const {originWallet = {}} = props;
    const [recipientWalletId, setRecipientWalletId] = useState();
    const [senderWalletId, setSenderWalletId] = useState(originWallet.id);
    const [amount, setAmount] = useState();
    const [errors, setErrors] = useState({});
    const dispatch = useDispatch();

    useEffect(() => {
        setSenderWalletId(originWallet.id)
    }, [originWallet.id]);

    const fieldsMapping = {
        amount: setAmount,
        recipientWalletId: setRecipientWalletId,
        senderWalletId: setSenderWalletId
    };

    const onSubmit = async () => {
        const transferRequest = {
            senderWalletId: senderWalletId,
            recipientWalletId: recipientWalletId,
            amount: amount
        }

        const response = await transfer(transferRequest);
        if (response.status === 200) {
            dispatch(updateUsers(response))
        } else {
            setErrors(response.data.errors);
        }
    }

    const onFieldChange = (e, fieldName) => {
        errors[fieldName] = null;
        errors["formErrors"] = null;
        setErrors(errors);
        fieldsMapping[fieldName](e.target.value);
    }

    return (<Card>
            <Card.Header>Transfer</Card.Header>
            <Card.Body>
                <Form noValidate>
                    <Form.Row>
                        {!!originWallet.id || <Col>
                            <Form.Label>Sender Wallet ID</Form.Label>
                        </Col>}
                        <Col>
                            <Form.Label>Destination Wallet ID</Form.Label>
                        </Col>
                        <Col>
                            <Form.Label>Amount</Form.Label>
                        </Col>
                        <Col>
                            <Form.Label/>
                        </Col>
                    </Form.Row>
                    <Form.Row>
                        {!!originWallet.id ||
                        <FieldControl placeholder="Wallet ID" onChange={onFieldChange} fieldName="senderWalletId"
                                      errors={errors}/>}
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