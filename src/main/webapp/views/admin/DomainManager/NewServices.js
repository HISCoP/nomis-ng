import React, {useState, useEffect} from 'react';
import {  Modal, ModalHeader, ModalBody,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { createDomainService, updateDomainService } from "../../../actions/domainsServices";
import { Spinner } from 'reactstrap';



const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const NewDomainService = (props) => {
    const [loading, setLoading] = useState(false)
    const defaultValues = {name:"",domainId:"",serviceType:"" }
    const [formData, setFormData] = useState(defaultValues)
    const classes = useStyles()
    //domainDetails

    useEffect(() => {
        getServices()
        //for Domain Area edit, load form data
            if(props.currentDomainService){
            setFormData({...formData, name:props.currentDomainService.name, domainId:props.currentDomainService.domainId, serviceType:props.currentDomainService.serviceType, code:props.currentDomainService.code});
        }
    }, [props.currentDomainService,  props.showModal]); //componentDidMount

const getServices = () => {
    props.loadDomainsServices();
    //const domainServicesList=props.loadDomainsServices();
}; //componentDidMount

    const handleNameInputChange = e => {

        setFormData ({ ...formData, [e.target.name]: e.target.value  });
    }
    const handleServiceTypeChange = (e) => {
        setFormData ({ ...formData, serviceType: e.target.value});
        
    };

    const createDomainService = e => {
        e.preventDefault()
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
            props.loadDomainsServices();
            props.toggleModal()
        }
        const onError = () => {
            setLoading(false);
                       
        }
        if(props.currentDomainService){
            props.updateDomainService(props.currentDomainService.id, formData, onSuccess, onError)
            return
        }
        formData.domainId= props.domainDetails.id
        props.createDomainService(formData, onSuccess,onError)

    }
    return (

        <div >
            <ToastContainer />
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="md">

                <Form onSubmit={createDomainService}>
                    <ModalHeader toggle={props.toggleModal}>New Service </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
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
                                    <Label for="serviceType">Service Type </Label>
                                    <Input
                                        type="select"
                                        name="serviceType"
                                        id="serviceType"
                                        value={formData.serviceType}
                                        onChange={handleServiceTypeChange}
                                        required
                                        >
                                        <option value=""> </option>
                                        <option value="1"> OVC</option>
                                        <option value="2"> Caregiver</option>
                                        <option value="3"> Others</option>
                                    </Input>
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

export default connect(null, { createDomainService, updateDomainService })(NewDomainService);
