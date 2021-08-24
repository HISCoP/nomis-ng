import React, {useState, useEffect} from 'react';
import {  Modal, ModalHeader, ModalBody, FormFeedback,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";


import { Spinner } from 'reactstrap';



const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const NewDomain = (props) => {
    const [loading, setLoading] = useState(false)
    const defaultValues = {name:"",description:"",format:"" }
    const [formData, setFormData] = useState(defaultValues)
    const [errors, setErrors] = useState({});
    const classes = useStyles()



    const handleInputChange = e => {
        setFormData ({ ...formData, [e.target.name]: e.target.value});
    }

    const handleNameInputChange = e => {

        setFormData ({ ...formData, [e.target.name]: e.target.value.split(" ").join("")  });
    }

    const validate = () => {
        let temp = { ...errors }
        temp.name = formData.name ? "" : "Name is required"
        setErrors({
            ...temp
        })
        console.log(temp)
        return Object.values(temp).every(x => x == "")
    }

    const createGlobalVariable = e => {
        // e.preventDefault()
        // setLoading(true);

        // const onSuccess = () => {
        //     setLoading(false);
        //     toast.success("Global variable saved successfully!")
        //     props.loadGlobalVariable();
        //     props.toggleModal()
        // }
        // const onError = () => {
        //     setLoading(false);
        //     toast.error("Something went wrong, please contact administration");
        // }

        // if(formData.id){
        //     props.updateGlobalVariable(formData.id, formData, onSuccess, onError)
        //     return
        // }
        // props.newGlobalVariable(formData, onSuccess,onError)

    }
    return (

        <div >
            <ToastContainer />
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg">

                <Form onSubmit={createGlobalVariable}>
                    <ModalHeader toggle={props.toggleModal}>New Application Codeset </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Codeset Group</Label>
                                            <Input
                                                type='text'
                                                name='name'
                                                id='name'
                                                placeholder=' '
                                                value={formData.name}
                                                onChange={handleNameInputChange}
                                                required
                                            />

                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Name</Label>
                                            <Input
                                                type='text'
                                                name='name'
                                                id='name'
                                                placeholder=' '
                                                value={formData.name}
                                                onChange={handleNameInputChange}
                                                required
                                            />

                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Language</Label>
                                            <Input
                                                type='text'
                                                name='name'
                                                id='name'
                                                placeholder=' '
                                                value={formData.name}
                                                onChange={handleNameInputChange}
                                                required
                                            />

                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Version</Label>
                                            <Input
                                                type='text'
                                                name='name'
                                                id='name'
                                                placeholder=' '
                                                value={formData.name}
                                                onChange={handleNameInputChange}
                                                required
                                            />

                                        </FormGroup>
                                    </Col>
                                    
                                </Row>

                                <MatButton
                                    type='submit'
                                    variant='contained'
                                    color='primary'
                                    className={classes.button}
                                    startIcon={<SaveIcon />}
                                    disabled={loading}>
                                    Save  {loading ? <Spinner /> : ""}
                                </MatButton>
                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={props.toggleModal}
                                    startIcon={<CancelIcon />}>
                                    Cancel
                                </MatButton>
                            </CardBody>
                        </Card>
                    </ModalBody>

                </Form>
            </Modal>
        </div>
    );
}

export default NewDomain;
