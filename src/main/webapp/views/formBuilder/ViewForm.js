import React, {useRef, useEffect, useState} from 'react';
import {  Errors, Form, FormBuilder } from 'react-formio';
import {Card,CardContent,} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import {fetchById, updateForm, fetchService} from '../../actions/formBuilder'
import MatButton from '@material-ui/core/Button';
import { authHeader } from '../../_helpers/auth-header';
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import {
    FormGroup,
    Input,
    Label,
    Col,
    Row,
    Modal, ModalBody, ModalHeader, CardBody, ModalFooter
} from 'reactstrap';
import {Link} from 'react-router-dom';
import CheckBoxOutlineBlankIcon from "@material-ui/icons/CheckBoxOutlineBlank";
import CheckBoxIcon from "@material-ui/icons/CheckBox";
import axios from 'axios';
import {url} from '../../api';
import CancelIcon from "@material-ui/icons/Cancel";
import DownloadLink  from "react-download-link";
import { Alert } from '@material-ui/lab';
import LinearProgress from "@material-ui/core/LinearProgress";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import 'formiojs/dist/formio.builder.min.css';

const useStyles = makeStyles(theme => ({
    root2: {
        width: '100%',
        height: 100,
        overflow: 'auto',
    }
}));

const Update = props => {
    const [res, setRes] = React.useState("");
    const [loading, setLoading] = React.useState(true);
    const [disabledCheckBox, setdisabledCheckBox] = useState(true)
    const [row, setRow] = useState(props.location.state && props.location.state.row ? props.location.state.row : "");
    const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
    const checkedIcon = <CheckBoxIcon fontSize="small" />;
    const [displayType, setDisplayType] = React.useState("");
    const [formCode, setformCode] = React.useState();
    //const [FormPrecedenceList, setFormPrecedenceList] = React.useState();
    const [form2, setform2] = React.useState();
    const classes = useStyles();
    let myform;
    const submission = props.patient;
    const textAreaRef = useRef(null);
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)

    let fileReader;
    const [showFileImport, setShowFileImport] = useState(true);
    const toggleShowFileImport = () => setShowFileImport(!showFileImport);

    useEffect(() => {
        async function fetchForms() {
            try {
                const response = await axios(url + "forms");
                const body = response.data;
                const data = body.map(({ name, code }) => ({ title: name, value: code }));
               // setFormPrecedenceList(data);
                body !== null ? setdisabledCheckBox(false) : setdisabledCheckBox(true)
            } catch (error) {
            }
        }
        fetchForms();
    }, []);

    useEffect(() => {
        async function fetchFormByCode() {
            axios
                .get(`${url}forms/formCode?formCode=${row.code}`)
                .then(response => {
                    setform2(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    toast.error('Could not load form resource, please contact admin.')
                    setLoading(false);
                })
        }
        fetchFormByCode();
    }, []);

    const handleFileRead = (e) => {
        const content = fileReader.result;
        setRow( {...JSON.parse(content), ...{id: row.id, code: row.code, name: row.name} });
        setform2({...JSON.parse(content), ...{id: row.id, code: row.code, name: row.name} });
        setRes(content.resourceObject);
    }

    const handleFileChosen = (file) => {
        fileReader = new FileReader();
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
    };

    useEffect (() => {
        props.fetchService()
    }, [])

    const handleSubmit = e =>  {
        form2['resourceObject'] = JSON.parse(res);
        props.updateForm(form2.id, form2, setLoading);
    }


    return (
        <Card>
            <ToastContainer />
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{  pathname: "/form-home",
                        state: 'form-builder'}} >
                        Form Manager
                    </Link>
                    <Typography color="textPrimary">Edit Form -  {row ? row.name : ""}</Typography>

                </Breadcrumbs>
                <br/>

                <Row>
                    <Col md={12}>
                        {showFileImport && <>
                            <Alert onClose={toggleShowFileImport} icon={false} className={"mb-3"}>
                                <h4>Import Form from a <b>(.json)</b> file</h4>
                                <input type="file" id="file" className="input-file mb-4" accept='.json'
                                       onChange={e => handleFileChosen(e.target.files[0])}/>
                            </Alert>
                        </>
                        }
                        {loading &&
                        <LinearProgress color="primary" thickness={5}/>
                        }
                        <Card >
                            <CardContent>

                                <Row>
                                    <Col md={4}> <FormGroup>
                                        <Label class="sr-only">Display Type</Label>
                                        <Input type="select"  id="displayType" value={displayType} onChange={e => setDisplayType(e.target.value)}>
                                            <option value="form">Form</option>
                                            <option value="wizard">Wizard</option></Input>
                                    </FormGroup></Col>

                                    <Col md={2}> <FormGroup>
                                        <label class="sr-only" ></label>
                                        <button type="button"  class="form-control btn btn-primary mt-4" onClick={() => handleSubmit()}>Update Form</button>
                                    </FormGroup></Col>
                                    <Col md={2}>
                                        <div onClick={toggleModal}  className="mt-5" style={{cursor:"pointer", color:"blue"}}>Preview Form</div>
                                    </Col>
                                </Row>
                                {/*only render form when loading is false and form2 has a value*/}
                                { !loading && form2 ?
                                    <FormBuilder form={form2.resourceObject || {}} {...props}
                                                 submission={{data :{baseUrl:url}}}
                                                 onChange={(schema) => {
                                                     // console.log(JSON.stringify(schema));
                                                     setRes(JSON.stringify(schema));
                                                 }} />
                                    : ""
                                }
                                <br></br>
                            </CardContent>
                        </Card>
                    </Col>
                </Row>
                <hr></hr>
                <Card >
                    <CardContent>
                        <h4>Json Form</h4>
                        <DownloadLink
                            label="Export as a json file"
                            filename={form2 ? form2.name+".json" : "lamisplus-form.json"}
                            exportFile={() => JSON.stringify(form2)}
                        /> Or Copy the json object below. <br/>

                        <div >
                    <textarea cols="100"
                              ref={textAreaRef}
                              value={res}/>
                        </div>
                    </CardContent>
                </Card>


                {/*preview modal start*/}
                <Modal isOpen={showModal} toggle={toggleModal} size="xl">
                    <ModalHeader toggle={toggleModal}><h4>View Form</h4> </ModalHeader>
                    <ModalBody>
                        <Card>
                            <CardContent>

                                <hr />
                                <Errors errors={props.errors} />
                                {!res ? "" :
                                    <Form
                                        form={JSON.parse(res)}
                                        ref={form => myform = form}
                                        submission={{data : {authHeader: authHeader(), baseUrl:url}}}
                                        //src={url}
                                        hideComponents={props.hideComponents}
                                        //onSubmit={props.onSubmit}
                                        onSubmit={(submission) => {
                                            console.log(submission);
                                            return fetch('https://lp-base-app.herokuapp.com/api/', {
                                                body: JSON.stringify(submission),
                                                headers: {
                                                    'content-type': 'application/json'
                                                },
                                                method: 'POST',
                                                mode: 'cors',
                                            }).then(res => {
                                                console.log(res);
                                              //  myform.emit('submitDone', submission);
                                            })}}
                                    />
                                }
                                <br></br>
                            </CardContent>
                        </Card>
                    </ModalBody>
                    <ModalFooter>
                        <MatButton
                            variant='contained'
                            color='default'
                            onClick={toggleModal}
                            startIcon={<CancelIcon />}
                        >
                            Cancel
                        </MatButton>
                    </ModalFooter>
                </Modal>
                <hr></hr>

            </CardBody>
        </Card>
    );
}

const mapStateToProps =  (state = { form:{}}) => {
    return {
        services: state.formReducers.services,
        formList: state.formReducers.form,
    }}

const mapActionsToProps = ({
    fetchById: fetchById,
    updateForm: updateForm,
    fetchService: fetchService,
})

export default connect(mapStateToProps, mapActionsToProps)(Update)