package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.opencsv.bean.CsvBindByName;

/**
 * CSV object for a tag in the M2Web EBD tag list response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonEBDTag {

  /**
   * The value of the {@code ID} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ID", required = true)
  private int id;

  /**
   * The value of the {@code Name} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Name", required = true)
  private String name;

  /**
   * The value of the {@code Description} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Description", required = false)
  private String description;

  /**
   * The value of the {@code ServerName} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ServerName", required = true)
  private String serverName;

  /**
   * The value of the {@code TopicName} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "TopicName")
  private String topicName;

  /**
   * The value of the {@code Address} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Address", required = true)
  private String address;

  /**
   * The value of the {@code Coef} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Coef", required = true)
  private double coef;

  /**
   * The value of the {@code Offset} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Offset", required = true)
  private double offset;

  /**
   * The value of the {@code LogEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "LogEnabled", required = true)
  private int logEnabled;

  /**
   * The value of the {@code AlEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlEnabled", required = true)
  private int alEnabled;

  /**
   * The value of the {@code AlBool} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlBool", required = true)
  private int alBool;

  /**
   * The value of the {@code MemTag} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "MemTag", required = true)
  private int memTag;

  /**
   * The value of the {@code MbsTcpEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "MbsTcpEnabled", required = true)
  private int mbsTcpEnabled;

  /**
   * The value of the {@code MbsTcpFloat} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "MbsTcpFloat", required = true)
  private int mbsTcpFloat;

  /**
   * The value of the {@code SnmpEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "SnmpEnabled", required = true)
  private int snmpEnabled;

  /**
   * The value of the {@code RTLogEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "RTLogEnabled", required = true)
  private int rtLogEnabled;

  /**
   * The value of the {@code AlAutoAck} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlAutoAck", required = true)
  private int alAutoAck;

  /**
   * The value of the {@code ForceRO} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ForceRO", required = true)
  private int forceRo;

  /**
   * The value of the {@code SnmpOID} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "SnmpOID", required = true)
  private int snmpOid;

  /**
   * The value of the {@code AutoType} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AutoType", required = true)
  private int autoType;

  /**
   * The value of the {@code AlHint} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlHint")
  private String alHint;

  /**
   * The value of the {@code AlHigh} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlHigh", required = true)
  private double alHigh;

  /**
   * The value of the {@code AlLow} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlLow", required = true)
  private double alLow;

  /**
   * The value of the {@code AlTimeDB} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlTimeDB", required = true)
  private int alTimeDb;

  /**
   * The value of the {@code AlLevelDB} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlLevelDB", required = true)
  private double alLevelDb;

  /**
   * The value of the {@code IVGroupA} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "IVGroupA", required = true)
  private int ivGroupA;

  /**
   * The value of the {@code IVGroupB} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "IVGroupB", required = true)
  private int ivGroupB;

  /**
   * The value of the {@code IVGroupC} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "IVGroupC", required = true)
  private int ivGroupC;

  /**
   * The value of the {@code IVGroupD} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "IVGroupD", required = true)
  private int ivGroupD;

  /**
   * The value of the {@code PageId} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "PageId", required = true)
  private int pageId;

  /**
   * The value of the {@code RTLogWindow} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "RTLogWindow", required = true)
  private int rtLogWindow;

  /**
   * The value of the {@code RTLogTimer} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "RTLogTimer", required = true)
  private int rtLogTimer;

  /**
   * The value of the {@code LogDB} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "LogDB", required = true)
  private double logDB;

  /**
   * The value of the {@code LogTimer} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "LogTimer", required = true)
  private int logTimer;

  /**
   * The value of the {@code AlLoLo} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlLoLo")
  private int alLoLo;

  /**
   * The value of the {@code AlHiHi} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlHiHi")
  private int alHiHi;

  /**
   * The value of the {@code MbsTcpRegister} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "MbsTcpRegister", required = true)
  private int mbsTcpRegister;

  /**
   * The value of the {@code MbsTcpCoef} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "MbsTcpCoef", required = true)
  private double mbsTcpCoef;

  /**
   * The value of the {@code MbsTcpOffset} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "MbsTcpOffset", required = true)
  private double mbsTcpOffset;

  /**
   * The value of the {@code EEN} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "EEN")
  private int een;

  /**
   * The value of the {@code ETO} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ETO")
  private String eto;

  /**
   * The value of the {@code ECC} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ECC")
  private String ecc;

  /**
   * The value of the {@code ESU} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ESU")
  private String esu;

  /**
   * The value of the {@code EAT} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "EAT")
  private String eat;

  /**
   * The value of the {@code ESH} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ESH")
  private String esh;

  /**
   * The value of the {@code SEN} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "SEN")
  private int sen;

  /**
   * The value of the {@code STO} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "STO")
  private String sto;

  /**
   * The value of the {@code SSU} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "SSU")
  private String ssu;

  /**
   * The value of the {@code TEN} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "TEN")
  private int ten;

  /**
   * The value of the {@code TSU} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "TSU")
  private String tsu;

  /**
   * The value of the {@code FEN} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "FEN")
  private int fen;

  /**
   * The value of the {@code FFN} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "FFN")
  private String ffn;

  /**
   * The value of the {@code FCO} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "FCO")
  private String fco;

  /**
   * The value of the {@code KPI} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "KPI")
  private int kpi;

  /**
   * The value of the {@code UseCustomUnit} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "UseCustomUnit")
  private int useCustomUnit;

  /**
   * The value of the {@code Type} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Type", required = true)
  private int type;

  /**
   * The value of the {@code Unit} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Unit")
  private String unit;

  /**
   * The value of the {@code AlStat} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlStat", required = true)
  private int alStat;

  /**
   * The value of the {@code ChangeTime} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "ChangeTime", required = true)
  private String changeTime;

  /**
   * The value of the {@code TagValue} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "TagValue")
  private String tagValue;

  /**
   * The value of the {@code TagQuality} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "TagQuality", required = true)
  private int tagQuality;

  /**
   * The value of the {@code AlType} column for a tag in the M2Web EBD tag list response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlType", required = true)
  private int alType;

  /**
   * Gets the value of the {@code Id} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Id} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the value of the {@code Name} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Name} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the value of the {@code Description} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Description} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the value of the {@code ServerName} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code ServerName} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public String getServerName() {
    return serverName;
  }

  /**
   * Gets the value of the {@code TopicName} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code TopicName} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getTopicName() {
    return topicName;
  }

  /**
   * Gets the value of the {@code Address} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Address} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getAddress() {
    return address;
  }

  /**
   * Gets the value of the {@code Coef} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Coef} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public double getCoef() {
    return coef;
  }

  /**
   * Gets the value of the {@code Offset} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Offset} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public double getOffset() {
    return offset;
  }

  /**
   * Gets the value of the {@code LogEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code LogEnabled} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getLogEnabled() {
    return logEnabled;
  }

  /**
   * Gets the value of the {@code AlEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlEnabled} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlEnabled() {
    return alEnabled;
  }

  /**
   * Gets the value of the {@code AlBool} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlBool} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlBool() {
    return alBool;
  }

  /**
   * Gets the value of the {@code MemTag} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code MemTag} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getMemTag() {
    return memTag;
  }

  /**
   * Gets the value of the {@code MbsTcpEnabled} column for a tag in the M2Web EBD tag list
   * response.
   *
   * @return The value of the {@code MbsTcpEnabled} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getMbsTcpEnabled() {
    return mbsTcpEnabled;
  }

  /**
   * Gets the value of the {@code MbsTcpFloat} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code MbsTcpFloat} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getMbsTcpFloat() {
    return mbsTcpFloat;
  }

  /**
   * Gets the value of the {@code SnmpEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code SnmpEnabled} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getSnmpEnabled() {
    return snmpEnabled;
  }

  /**
   * Gets the value of the {@code RTLogEnabled} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code RTLogEnabled} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getRtLogEnabled() {
    return rtLogEnabled;
  }

  /**
   * Gets the value of the {@code AlAutoAck} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlAutoAck} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlAutoAck() {
    return alAutoAck;
  }

  /**
   * Gets the value of the {@code ForceRO} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code ForceRO} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getForceRo() {
    return forceRo;
  }

  /**
   * Gets the value of the {@code SnmpOID} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code SnmpOID} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getSnmpOid() {
    return snmpOid;
  }

  /**
   * Gets the value of the {@code AutoType} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AutoType} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAutoType() {
    return autoType;
  }

  /**
   * Gets the value of the {@code AlHint} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlHint} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getAlHint() {
    return alHint;
  }

  /**
   * Gets the value of the {@code AlHigh} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlHigh} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public double getAlHigh() {
    return alHigh;
  }

  /**
   * Gets the value of the {@code AlLow} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlLow} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public double getAlLow() {
    return alLow;
  }

  /**
   * Gets the value of the {@code AlTimeDB} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlTimeDB} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public double getAlTimeDb() {
    return alTimeDb;
  }

  /**
   * Gets the value of the {@code AlLevelDB} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlLevelDB} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public double getAlLevelDb() {
    return alLevelDb;
  }

  /**
   * Gets the value of the {@code IVGroupA} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code IVGroupA} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getIvGroupA() {
    return ivGroupA;
  }

  /**
   * Gets the value of the {@code IVGroupB} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code IVGroupB} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getIvGroupB() {
    return ivGroupB;
  }

  /**
   * Gets the value of the {@code IVGroupC} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code IVGroupC} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getIvGroupC() {
    return ivGroupC;
  }

  /**
   * Gets the value of the {@code IVGroupD} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code IVGroupD} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getIvGroupD() {
    return ivGroupD;
  }

  /**
   * Gets the value of the {@code PageId} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code PageId} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getPageId() {
    return pageId;
  }

  /**
   * Gets the value of the {@code RTLogWindow} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code RTLogWindow} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getRtLogWindow() {
    return rtLogWindow;
  }

  /**
   * Gets the value of the {@code RTLogTimer} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code RTLogTimer} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getRtLogTimer() {
    return rtLogTimer;
  }

  /**
   * Gets the value of the {@code LogDB} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code LogDB} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public double getLogDB() {
    return logDB;
  }

  /**
   * Gets the value of the {@code LogTimer} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code LogTimer} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getLogTimer() {
    return logTimer;
  }

  /**
   * Gets the value of the {@code AlLoLo} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlLoLo} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlLoLo() {
    return alLoLo;
  }

  /**
   * Gets the value of the {@code AlHiHi} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlHiHi} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlHiHi() {
    return alHiHi;
  }

  /**
   * Gets the value of the {@code MbsTcpRegister} column for a tag in the M2Web EBD tag list
   * response.
   *
   * @return The value of the {@code MbsTcpRegister} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getMbsTcpRegister() {
    return mbsTcpRegister;
  }

  /**
   * Gets the value of the {@code MbsTcpCoef} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code MbsTcpCoef} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public double getMbsTcpCoef() {
    return mbsTcpCoef;
  }

  /**
   * Gets the value of the {@code MbsTcpOffset} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code MbsTcpOffset} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public double getMbsTcpOffset() {
    return mbsTcpOffset;
  }

  /**
   * Gets the value of the {@code EEN} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code EEN} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getEen() {
    return een;
  }

  /**
   * Gets the value of the {@code ETO} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code ETO} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getEto() {
    return eto;
  }

  /**
   * Gets the value of the {@code ECC} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code ECC} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getEcc() {
    return ecc;
  }

  /**
   * Gets the value of the {@code ESU} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code ESU} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getEsu() {
    return esu;
  }

  /**
   * Gets the value of the {@code EAT} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code EAT} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getEat() {
    return eat;
  }

  /**
   * Gets the value of the {@code ESH} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code ESH} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getEsh() {
    return esh;
  }

  /**
   * Gets the value of the {@code SEN} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code SEN} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getSen() {
    return sen;
  }

  /**
   * Gets the value of the {@code STO} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code STO} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getSto() {
    return sto;
  }

  /**
   * Gets the value of the {@code SSU} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code SSU} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getSsu() {
    return ssu;
  }

  /**
   * Gets the value of the {@code TEN} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code TEN} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getTen() {
    return ten;
  }

  /**
   * Gets the value of the {@code TSU} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code TSU} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getTsu() {
    return tsu;
  }

  /**
   * Gets the value of the {@code FEN} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code FEN} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getFen() {
    return fen;
  }

  /**
   * Gets the value of the {@code FFN} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code FFN} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getFfn() {
    return ffn;
  }

  /**
   * Gets the value of the {@code FCO} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code FCO} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getFco() {
    return fco;
  }

  /**
   * Gets the value of the {@code KPI} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code KPI} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getKpi() {
    return kpi;
  }

  /**
   * Gets the value of the {@code UseCustomUnit} column for a tag in the M2Web EBD tag list
   * response.
   *
   * @return The value of the {@code UseCustomUnit} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getUseCustomUnit() {
    return useCustomUnit;
  }

  /**
   * Gets the value of the {@code Type} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Type} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getType() {
    return type;
  }

  /**
   * Gets the value of the {@code Unit} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Unit} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Gets the value of the {@code AlStat} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlStat} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlStat() {
    return alStat;
  }

  /**
   * Gets the value of the {@code ChangeTime} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code ChangeTime} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public String getChangeTime() {
    return changeTime;
  }

  /**
   * Gets the value of the {@code TagValue} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code TagValue} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getTagValue() {
    return tagValue;
  }

  /**
   * Gets the value of the {@code TagQuality} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code TagQuality} column for a tag in the M2Web EBD tag list
   *     response.
   * @since 1.0.0
   */
  public int getTagQuality() {
    return tagQuality;
  }

  /**
   * Gets the value of the {@code AlType} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlType} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlType() {
    return alType;
  }
}
