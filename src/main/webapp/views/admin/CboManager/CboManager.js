import React, {useEffect} from 'react';
import MaterialTable from 'material-table';
import {
    Card,
    CardBody, Modal, ModalBody, ModalHeader, Spinner, ModalFooter
} from 'reactstrap';
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import { Link } from 'react-router-dom';
import Button from "@material-ui/core/Button";
import { FaPlus } from "react-icons/fa";
import "@reach/menu-button/styles.css";
import { connect } from "react-redux";
import { fetchAllCodeset, deleteApplicationCodeset } from "../../../actions/codeSet";
import NewCbo from "./NewCbo";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import { toast } from "react-toastify";
import SaveIcon from "@material-ui/icons/Delete";
import CancelIcon from "@material-ui/icons/Cancel";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const ApplicationCodesetList = (props) => {
    const [loading, setLoading] = React.useState(true);
    const [deleting, setDeleting] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    const [currentCodeset, setCurrentCodeset] = React.useState(null);
    const [showDeleteModal, setShowDeleteModal] = React.useState(false);
    const toggleDeleteModal = () => setShowDeleteModal(!showDeleteModal)
    const classes = useStyles()
    useEffect(() => {
        loadApplicationCodeset()
    }, []); //componentDidMount

 const loadApplicationCodeset = () => {
  
    setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)     
        }
            props.fetchAllCApplicationCodeset(onSuccess, onError);
    }; //componentDidMount

    const openNewDomainModal = (row) => {
        setCurrentCodeset(row);
        toggleModal();
    }

    const processDelete = (id) => {
        setDeleting(true);
       const onSuccess = () => {
           setDeleting(false);
           toggleDeleteModal();
           loadApplicationCodeset();
       };
       const onError = () => {
           setDeleting(false);
           toast.error("Something went wrong, please contact administration");
       };
       props.delete(id, onSuccess, onError);
       }
       const openApplicationCodeset = (row) => {
           setCurrentCodeset(row);
           toggleModal();
       }
   
       const deleteApplicationCodeset = (row) => {
           setCurrentCodeset(row);
           toggleDeleteModal();
       }

    return (
        <Card>
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">CBO  </Typography>
                </Breadcrumbs>
                <br/>
                <div className={"d-flex justify-content-end pb-2"}>
                    <Button variant="contained"
                            color="primary"
                            startIcon={<FaPlus />}
                            onClick={() => openNewDomainModal(null)}
                            >
                        <span style={{textTransform: 'capitalize'}}>Add New CBO </span>
                    </Button>

                </div>
                <MaterialTable
                    title="Find CBO"
                    columns={[
                    {
                        title: "ID",
                        field: "codesetGroup",
                    },
                    { title: "Name", field: "name" },
                    { title: "Description", field: "version" },

                    
                ]}
                isLoading={loading}
                data={props.applicationCodesetList.map((row) => ({
                    codesetGroup: 'Lagos/Aja/003/002',
                    name: 'Emeka Emeka',
                    display: 'Heart Foundation CBO',
                }))}
                actions= {[
                    {
                        icon: EditIcon,
                        iconProps: {color: 'primary'},
                        tooltip: 'Edit CBO',
                        onClick: (event, row) => openApplicationCodeset(row)
                    },
                    {
                        icon: DeleteIcon,
                        iconProps: {color: 'primary'},
                        tooltip: 'Delete CBO',
                        onClick: (event, row) => deleteApplicationCodeset(row)
                    }
                        ]}
                    
                        //overriding action menu with props.actions
                        components={props.actions}
                        options={{
                            headerStyle: {
                                backgroundColor: "#9F9FA5",
                                color: "#000",
                            },
                            searchFieldStyle: {
                                width : '200%',
                                margingLeft: '250px',
                            },
                            filtering: true,
                            exportButton: false,
                            searchFieldAlignment: 'left',
                            actionsColumnIndex: -1
                        }}
                />
            </CardBody>
            <NewCbo toggleModal={toggleModal} showModal={showModal} loadApplicationCodeset={props.applicationCodesetList} formData={currentCodeset} loadCodeset={loadApplicationCodeset}/>
            {/*Delete Modal for Application Codeset */}
            <Modal isOpen={showDeleteModal} toggle={toggleDeleteModal} >
                    <ModalHeader toggle={props.toggleDeleteModal}> Delete CBO  - {currentCodeset && currentCodeset.display ? currentCodeset.display : ""} </ModalHeader>
                    <ModalBody>
                        <p>Are you sure you want to proceed ?</p>
                    </ModalBody>
                <ModalFooter>
                    <Button
                        type='button'
                        variant='contained'
                        color='primary'
                        className={classes.button}
                        startIcon={<SaveIcon />}
                        disabled={deleting}
                        onClick={() => processDelete(currentCodeset.id)}
                    >
                        Delete  {deleting ? <Spinner /> : ""}
                    </Button>
                    <Button
                        variant='contained'
                        color='default'
                        onClick={toggleDeleteModal}
                        startIcon={<CancelIcon />}
                    >
                        Cancel
                    </Button>
                </ModalFooter>
        </Modal>
        </Card>
        
    );
   

}


const mapStateToProps = state => {
    return {
        applicationCodesetList: state.codesetsReducer.codesetList
    };
  };
  const mapActionToProps = {
    fetchAllCApplicationCodeset: fetchAllCodeset,
    delete: deleteApplicationCodeset
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ApplicationCodesetList);
