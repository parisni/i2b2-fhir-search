package edu.harvard.i2b2.crc.dao.setfinder.querybuilder.temporal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.harvard.i2b2.common.exception.I2B2DAOException;
import edu.harvard.i2b2.common.exception.I2B2Exception;
import edu.harvard.i2b2.common.util.ServiceLocator;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.crc.dao.DAOFactoryHelper;
import edu.harvard.i2b2.crc.dao.setfinder.CRCTimeOutException;
import edu.harvard.i2b2.crc.dao.setfinder.IPatientSetCollectionDao;
import edu.harvard.i2b2.crc.dao.setfinder.PatientSetCollectionSpringDao;
//import edu.harvard.i2b2.crc.dao.setfinder.QueryInstanceSpringDao;
import edu.harvard.i2b2.crc.dao.setfinder.QueryResultInstanceSpringDao;
import edu.harvard.i2b2.crc.dao.setfinder.QueryStatusTypeId;
import edu.harvard.i2b2.crc.dao.setfinder.querybuilder.QueryToolUtil;
import edu.harvard.i2b2.crc.datavo.db.DataSourceLookup;
import edu.harvard.i2b2.crc.datavo.i2b2message.PasswordType;
import edu.harvard.i2b2.crc.datavo.i2b2message.SecurityType;
import edu.harvard.i2b2.crc.datavo.ontology.ConceptType;
import edu.harvard.i2b2.crc.datavo.pdo.PidSet;
import edu.harvard.i2b2.crc.datavo.pdo.PidType.PatientMapId;
import edu.harvard.i2b2.crc.datavo.pm.CellDataType;
import edu.harvard.i2b2.crc.datavo.pm.ConfigureType;
//import edu.harvard.i2b2.crc.datavo.pm.ParamType;
//import edu.harvard.i2b2.crc.datavo.pm.ProjectType;
import edu.harvard.i2b2.crc.datavo.setfinder.query.ItemType;
import edu.harvard.i2b2.crc.datavo.setfinder.query.ItemType.ConstrainByDate;
import edu.harvard.i2b2.crc.datavo.setfinder.query.PanelType;
import edu.harvard.i2b2.crc.datavo.setfinder.query.QueryDefinitionType;
import edu.harvard.i2b2.crc.delegate.pm.CallPMUtil;
import edu.harvard.i2b2.crc.delegate.pm.CallFHIRUtil;
import edu.harvard.i2b2.crc.util.PMServiceAccountUtil;
//import edu.harvard.i2b2.crc.delegate.pm.PMServiceDriver;
import edu.harvard.i2b2.crc.util.ParamUtil;
import edu.harvard.i2b2.crc.util.QueryProcessorUtil;
import fr.aphp.wind.i2b2fhir.i2b2set.I2b2Set;
import fr.aphp.wind.i2b2fhir.i2b2setlist.I2b2SetList;


public class TemporalPanelFHIRQueryItem extends TemporalPanelItem {

	protected final Log log = LogFactory.getLog(getClass());

	protected String domain = null;

	public static String TEMP_TABLE = "FHIR_TEMP_TABLE";
	private String resultInstanceId;
	private DataSourceLookup dataSourceLookup;
	private String dataSourceName;
	private ItemType item;
	private String user;
	private String projId;
	private String patient_ide_sources = null;//for dynamically limiting result pid_set
	private int page_size = 100000;
	I2b2SetList pidSet = null;
	
	protected List<ItemType> itemList = new ArrayList<ItemType>();

	public TemporalPanelFHIRQueryItem(TemporalPanel parent, ItemType item) 
			throws I2B2Exception {
		super(parent, item);
		this.parent = parent;	
		dataSourceLookup = parent.getDataSourceLookup();
		dataSourceName = dataSourceLookup.getDataSource();
		this.item = item;
		//itemList.add(item);
		SecurityType st = parent.getSecurityType();
		user = st.getUsername();
		projId = parent.getProjectId();
		SecurityType stReq = parent.getRequestorSecurityType();		

	}

	public void add(ItemType item){
		itemList.add(item);
	}

	@Override
	protected String buildSql() throws I2B2DAOException {

		
		
		Connection manualConnection = null;
		PreparedStatement ps = null;
		
		conceptType = new ConceptType();
		conceptType.setColumnname(" result_instance_id ");
		conceptType.setOperator(" = ");
		conceptType.setFacttablecolumn(" patient_num ");
		conceptType.setTablename("qt_patient_set_collection ");
		conceptType.setDimcode(resultInstanceId);
		try {
			super.parseItem();
		} catch (I2B2Exception e) {
			// TODO Auto-generated catch block
		}
		log.debug("FHIR ITEM result instance: " + resultInstanceId);
		if (this.returnEncounterNum()||
				this.returnInstanceNum()
				){
			return super.buildSql();
		}
		else{

			try {
				QueryProcessorUtil qpUtil = QueryProcessorUtil.getInstance();

				SecurityType s = new PMServiceAccountUtil().getServiceSecurityType( parent.getRequestorSecurityType().getDomain());

		        ConfigureType useronfigure = CallPMUtil.getUserConfigure(s, qpUtil.getProjectManagementCellUrl(), this.domain, projId, "CRC");

		        if (useronfigure == null)
		                return null;

			    CellDataType cellData = CallPMUtil.getCellForProject(useronfigure.getCellDatas(), "FHIR");

				String sql = "select concept_cd from "
						+ this.getDbSchemaName()
						+ "concept_dimension  where concept_path like ?)";
				
				manualConnection = createConnection(dataSourceLookup);
				ps = manualConnection.prepareStatement(sql);
				ps.setString(1, item.getItemKey());
	ResultSet rs = ps.executeQuery(sql);
				
		while (rs.next()){
				pidSet = callFHIRUsingQueryDef(cellData.getUrl(), rs.getString(0));
		}
			} catch (AxisFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (I2B2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBUtilException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String query = "CREATE  TABLE " + TEMP_TABLE + " ( "
					+ " patient_ide varchar(200), " + " PATIENT_ide_source varchar(50))<*>";

			if (dataSourceLookup.getServerType().equalsIgnoreCase(
					DAOFactoryHelper.POSTGRESQL))
				query =  "CREATE TEMP  TABLE " + TEMP_TABLE + " ( "
						+ " patient_ide varchar(200), " + " PATIENT_ide_source varchar(50))<*>";


			if (dataSourceLookup.getServerType().equalsIgnoreCase(
					DAOFactoryHelper.SQLSERVER)) {
				query += "create index tempIndex on "
						+ getDbSchemaName()
						+ "#fhir_temp_table (patient_ide,patient_ide_source)\n<*>\n";

			}
			for( I2b2Set t : pidSet){
					
				query += "insert into " + TEMP_TABLE +" (PATIENT_ide,PATIENT_IDE_SOURCE) values ('"+t.getPatientUri()+"','FHIR')<*>";
				}

			
			if (dataSourceLookup.getServerType().equals(DAOFactoryHelper.ORACLE)) {
				query += "--endcreate select distinct  a.patient_num, 0 from patient_mapping a, " + TEMP_TABLE + " b where  a.patient_ide = b.patient_ide and a.patient_ide_source = b.patient_ide_source";
			} else if (dataSourceLookup.getServerType().equalsIgnoreCase(DAOFactoryHelper.SQLSERVER) ||
					dataSourceLookup.getServerType().equalsIgnoreCase(DAOFactoryHelper.POSTGRESQL)) { 
				query += "--endcreate select distinct  a.patient_num, 0 from patient_mapping a, " + TEMP_TABLE + " b where  a.patient_ide = b.patient_ide and a.patient_ide_source = b.patient_ide_source ";
			}
				return query;
		}


	}


	private I2b2SetList callFHIRUsingQueryDef(String url, String code) throws I2B2Exception, JAXBUtilException, AxisFault, UnirestException {
		

//"birthdate=1974-12-24"
		CallFHIRUtil crcUtil = new CallFHIRUtil(url, projId, this.patient_ide_sources, page_size);

		if (item == null)
			return null;
	    String searchQuery = code;

	    if (parent.hasPanelDateConstraint())
	    {
	    	searchQuery += "=" + parent.getFromDate();
	    }
		return crcUtil.callQueryDefinionType(searchQuery ,"patientSet");
	}



	@Override
	protected String getJoinTable() {
		if (returnInstanceNum()||
				hasItemDateConstraint()||
				hasPanelDateConstraint()||
				hasValueConstraint()||
				hasPanelOccurrenceConstraint()) {
			return "observation_fact";
		} else if (returnEncounterNum()) {
			return "visit_dimension";
		} else {
			return "qt_patient_set_collection";
		}
	}


	private Connection createConnection( DataSourceLookup dsLookup )
			throws Exception {
	
	Connection manualConnection = null;	
	
	
	try {
	
		manualConnection = ServiceLocator.getInstance()
				.getAppServerDataSource(dsLookup.getDataSource())
				.getConnection();
	
		
	} catch (Exception ex) {
		throw ex;
	}
	
	return manualConnection;
	
}


	private String getDbSchemaName() {
		if (dataSourceLookup.getFullSchema() != null && dataSourceLookup.getFullSchema().endsWith(".")) { 
			return dataSourceLookup.getFullSchema().trim();
		}
		else if (dataSourceLookup.getFullSchema() != null) { 
			return dataSourceLookup.getFullSchema().trim() + ".";
		}
		return null;
	}
}
