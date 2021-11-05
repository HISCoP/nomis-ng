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
import { fetchAll, deleteIp } from "../../../actions/ip";
import NewDonorManager from "./NewCboDonorIpManager";
import EditIcon from "@material-ui/icons/Edit";
//import DeleteIcon from "@material-ui/icons/Delete";
import { toast } from "react-toastify";
import SaveIcon from "@material-ui/icons/Delete";
import CancelIcon from "@material-ui/icons/Cancel";
import {makeStyles} from "@material-ui/core/styles";


const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const IpListManager = (props) => {
    const [loading, setLoading] = React.useState(true);
    const [deleting, setDeleting] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    const [currentIp, setcurrentIp] = React.useState(null);
    const [showDeleteModal, setShowDeleteModal] = React.useState(false);
    const toggleDeleteModal = () => setShowDeleteModal(!showDeleteModal)
    const classes = useStyles()
    useEffect(() => {
        loadIpList()
    }, []); //componentDidMount

 const loadIpList = () => {
  
    setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)     
        }
            props.fetchAllDonors(onSuccess, onError);
    }; //componentDidMount

    const openNewDomainModal = (row) => {
        setcurrentIp(row);
        toggleModal();
    }

    const processDelete = (id) => {
        setDeleting(true);
       const onSuccess = () => {
           setDeleting(false);
           toggleDeleteModal();
           loadIpList();
       };
       const onError = () => {
           setDeleting(false);
           toggleDeleteModal();
       };
       props.deleteIp(id, onSuccess, onError);
       }
       const openDonor = (row) => {
            setcurrentIp(row);
           toggleModal();
       }
   
    //    const deleteIpDetail = (row) => {
    //        setcurrentIp(row);
    //        toggleDeleteModal();
    //    }

    return (
        <Card>
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">CBO-DONOR-IP</Typography>
                </Breadcrumbs>
                <br/>
                <div className={"d-flex justify-content-end pb-2"}>
                    <Button variant="contained"
                            color="primary"
                            startIcon={<FaPlus />}
                            onClick={() => openNewDomainModal(null)}
                            >
                        <span style={{textTransform: 'capitalize'}}>Add New CBO-Donor-IP </span>
                    </Button>

                </div>
                <MaterialTable
                    title="Find Cbo"
                    columns={[
                    {
                        title: "ID",
                        field: "id",
                    },
                    {
                        title: "Name",
                        field: "name",
                    },
                    { title: "Description", field: "description" },
                    
                ]}
                isLoading={loading}
                data={props.ipList.map((row) => ({
                    id:row.id,
                    name: row.name,
                    description: row.description,

                }))}
                actions= {[
                    {
                        icon: EditIcon,
                        iconProps: {color: 'primary'},
                        tooltip: 'Edit Donor',
                        onClick: (event, row) => openDonor(row)
                    },
                    // {
                    //     icon: DeleteIcon,
                    //     iconProps: {color: 'primary'},
                    //     tooltip: 'Delete Donor',
                    //     onClick: (event, row) => deleteDonorDetail(row)
                    // }
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
            <NewDonorManager toggleModal={toggleModal} showModal={showModal} loadIpList={props.ipList} formData={currentIp} loadIps={loadIpList}/>
            {/*Delete Modal for Application Codeset */}
            <Modal isOpen={showDeleteModal} toggle={toggleDeleteModal} >
                    <ModalHeader toggle={props.toggleDeleteModal}> Delete Ip - {currentIp && currentIp.name ? currentIp.name : ""} </ModalHeader>
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
                        onClick={() => processDelete(currentIp.id)}
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
        ipList: state.ipReducer.ipList
    };
  };
  const mapActionToProps = {
    fetchAllDonors: fetchAll,
    deleteIp: deleteIp
  };
  
  export default connect(mapStateToProps, mapActionToProps)(IpListManager);
