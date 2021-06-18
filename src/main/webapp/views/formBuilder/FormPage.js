import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";

import { Card, CardContent } from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import { makeStyles } from "@material-ui/core/styles";
import Title from "../../views/Title/CardTitle";
import {fetchAllForms, deleteForm} from "./../../actions/formBuilder"
import MaterialTable from "material-table";
import { connect } from "react-redux";

import "react-widgets/dist/css/react-widgets.css";
import FormRendererModal from "./../formBuilder/FormRendererModal";
import { ToastContainer, toast } from "react-toastify";
import {Menu, MenuButton, MenuItem, MenuList} from '@reach/menu-button';
import { MdDeleteForever, MdModeEdit } from "react-icons/md";
import DownloadLink  from "react-download-link";


const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }
}));

const GeneralFormSearch = props => {
    const classes = useStyles();
    const [loading, setLoading] = useState(false);
    const [showCurrentForm, setShowCurrentForm] = useState(false);
    const [currentForm, setCurrentForm] = useState(false);

    useEffect(() => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
           
        };
        const onError = () => {
            setLoading(false);
           
        };
        props.fetchAllForms(onSuccess, onError);
    }, []);

    const onSuccess = () => {
        toast.success("Form saved successfully!", { appearance: "success" });
        setShowCurrentForm(false);
    };

    const onError = () => {
        toast.error("Something went wrong, request failed.");
        setShowCurrentForm(false);
    };

    const viewForm = (row) => {
        setCurrentForm({
            programCode: row.programCode,
            formName: "VIEW FORM",
            formCode: row.code,
            type: "VIEW",
            options: {
                modalSize: "modal-lg",
            },
        });
        setShowCurrentForm(true);
    };

    const onDelete = row => {
        if (window.confirm(`Are you sure you want to archive ${row.name} form ?`))
            props.deleteForm(row.id)
    }


    return (
        <div>
            <Card className={classes.cardBottom}>
                <CardContent>
                    <Title>

                            <Link color="inherit" to ={{
                                pathname: "form-builder",
                            }}  >
                            <Button
                                variant="contained"
                                color="primary"
                                className=" float-right mr-1"
                                startIcon={<AddIcon />}>
                                <span style={{textTransform: 'capitalize'}}>New </span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'lowercase'}}>Form </span>
                            </Button>
                        </Link>
                        <br />
                    </Title>
                    <br />
                    <MaterialTable
                    title="Basic Export Preview"
                    columns={[
                        { title: 'Name', field: 'name' },
                        { title: 'Surname', field: 'surname' },
                        { title: 'Birth Year', field: 'birthYear', type: 'numeric' },
                        {
                        title: 'Birth Place',
                        field: 'birthCity',
                        lookup: { 34: 'İstanbul', 63: 'Şanlıurfa' },
                        },
                    ]}
                    data={[
                        { name: 'Mehmet', surname: 'Baran', birthYear: 1987, birthCity: 63 },
                        { name: 'Zerya Betül', surname: 'Baran', birthYear: 2017, birthCity: 34 },
                    ]}        
                    options={{
                        exportButton: true
                    }}
                />
                </CardContent>
            </Card>
            <FormRendererModal
                programCode={currentForm.programCode}
                formCode={currentForm.formCode}
                showModal={showCurrentForm}
                setShowModal={setShowCurrentForm}
                currentForm={currentForm}
                onSuccess={onSuccess}
                onError={onError}
                options={currentForm.options}
            />
        </div>
    );
};

const mapStateToProps =  (state = { form:{}}) => {
     console.log(state.forms)
    return {
        formList: state.formReducers.form !==null ? state.formReducers.form : {},
    }}

const mapActionToProps = {
    fetchAllForms: fetchAllForms,
     deleteForm: deleteForm
};
export default connect(mapStateToProps, mapActionToProps)(GeneralFormSearch);
