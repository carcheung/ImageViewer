package com.example.carolyncheung.hisimageviewer.data;

/**
 * Created by carolyncheung on 1/7/18.
 */

public class IMGHeader {
    public char ImageFormat[] = new char[10];
    public short VersionOfStandardHeader;
    public long StandardHeaderSizeInBytes;
    public short VersionOfUserHeader;
    long UserHeaderSizeInBytes;
    // image frame info
    short NumberOfFrames;
    short NumberOfRowsInFrame;
    short NumberOfColsInFrame;
    short ImageDepthInBits;
    // general info
    char AcquisitionDate[] = new char[20];
    char AcquisitionTime[] = new char[20];
    char Dutid[] = new char[20];
    char Operator[] = new char[50];
    char DetectorSignature[] = new char[20];
    char TestSystemName[] = new char[20];
    char TestStationRevision[] = new char[20];
    char CoreBundleRevision[] = new char[20];
    char AcquisitionName[] = new char[40];
    char AcquisitionParameterRevision[] = new char[20];
    short OriginalNumberOfRow;
    short OriginalNumberOfColumns;
    short RowNumberUpperLeftPointArchiveROI;
    short ColNumberUpperLeftPointArchiveROI;
    short Swapped;
    short Reordered;
    short HorizontalFlipped;
    short VerticalFlipped;
    short WindowValueDesired;
    short LevelValueDesired;
    // acquisition techniques
    short AcquisitionMode;
    short acquisitionType;
    char UserAcquisitionCoffFileName1[] = new char[100];
    char UserAcquisitionCoffFileName2[] = new char[100];
    short FramesBeforeExposure;
    short FramesDuringExposure;
    short FramesAfterExposure;
    short IntervalBetweenFrames;
    double ExposureTimeDelayInMicrosecs;
    double TimeBetweenFramesInMicrosecs;
    short FramesToSkipExpose;
    short ExposureMode;
    double PrePresetTimeInMicrosecs;
    float AcquisitionFrameRateInFps;
    // detector Settings
    short FOVSelect;
    short ExpertMode;
    double SetVCommon1;
    double SetVCommon2;
    double SetAREF;
    long SetAREFTrime;
    double SetSpareVoltageSource;
    double SetCompensationVoltage;
    double SetRowOffVoltage;
    double SetRowOnVoltage;
    long StoreCompensationVoltage;
    short RampSelection;
    short TimingMode;
    short Bandwidth;
    short ARCIntegrator;
    short ARCPostIntegrator;
    long NumberOfRows;
    short RowEnable;
    short EnableStretch;
    short CompEnable;
    short CompStretch;
    short LeftEvenTristate;
    short RightOddTristate;
    long TestModeSelect;
    long AnalogTestSource;
    long VCommonSelect;
    long DRCColumnSum;
    long TestPatternFrameDelta;
    long TestPatternRowDelta;
    long TestPatternColumnDelta;
    short DetectorHorizontalFlip;
    short DetectorVerticalFlip;
    short DFNAutoScrubOnOffc;
    long FiberChannelTimeOutInMicrosecs;
    long DFNAutoScrubOnOff;
    short StoreAECROI;
    short TestPatternSaturationValue;
    long TestPatternSeed;
    // exposure settings
    float ExposureTimeInMillisecs;
    float FrameRateInFPS;
    // x-ray settings
    float kVP;
    float mA;
    float mAs;
    float FocalSpotInMM;
    char GeneratorType[] = new char[20];
    // lightsource
    float StrobeIntensityInFtl;
    // detector sensor readings
    double RefRegTemp1;
    double RefRegTemp2;
    double RefRegTemp3;
    float Humidity1;
    float Humidity2;
    double DetectorControlTemp;
    // dosimeter
    double DoseValueInmR;
    // target level checking
    short TargetLevelROIRow0;
    short TargetLevelROICol0;
    short TargetLevelROIRow1;
    short TargetLevelROICol1;
    short FrameNumberForTargetLevelROI;
    short PercentRangeForTargetLevel;
    short TargetValue;
    short ComputedMedianValue;
    // ramp info
    short LoadZero;
    short MaxLUTOut;
    short MinLUTOut;
    short MaxLinear;
    short Reserved;
    short ElectronsPerCount;
    short ModeGain;
    // misc additions
    double TemperatureInDegC;
    short LineRepaired;
    char LineRepairFileName[] = new char[100];
    float CurrentLongitudinalInMM;
    float CurrentTransverseInMM;
    float CurrentCircularInMM;
    long CurrentFilterSelection;
    // for LFOV Mammo with fiber DCB2
    short DisableScrubAck;
    short ScanModeSelect;
    // for TRAD (Ethernet)
    char DetectorAppSwVersion[] = new char[20];
    char DetectorNIOSVersion[] = new char[20];
    char DetectorPeripheralSetVerion[] = new char[20];
    char DetectorPhysicalAddress[] = new char[20];

    short PowerDown;

    double IntialVoltageLevel_VCOMMON;
    double FinalVoltageLevel_VCOMMON;
    // DMR & other parameters
    char DmrCollimatorSpotSize[] = new char[10];
    char DmrTrack[] = new char[5];
    char DmrFilter[] = new char[5];
    short FilterCarousel;
    char Phantom[] = new char[20];
    // programmable ramp parameters
    short SetEnableHightTime;
    short SetEnableLowTime;
    short SetCompHighTime;
    short SetCompLowTime;
    short setSyncLowTime;
    short SetConvertLowTime;
    short SetSyncHighTime;
    short setEOLTime;
    short SetRampOffsetTIme;
    // FOV Select command in Mobile
    short FOVStartingValue;
    short ColumnBinning;
    short RowBinning;
    short BorderColumns64;
    short BorderRows64;
    short FETOffRows64;
    short FOVStartColumn128;
    short FOVStartRow128;
    short NumberOfColumns128;
    short NumberOfRows128;
// new LFOV parameter in V8002
    double ChilleremperatureInDegC;
    char VFPAcquistion[] = new char[2000];
    char Comment[] = new char[200];
    char UserHeader[] = new char[4852];
}
