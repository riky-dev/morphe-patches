package app.riky.patches.komoot

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

private const val packageClass =
    "Lde/komoot/android/services/api/model/Package;"

/** Pre-2026.41.3 RegionsData (obfuscated wmt). */
internal object RegionsDataLegacyFingerprint : Fingerprint(
    definingClass = "Lwmt;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    parameters = listOf(
        "Z",
        "Ljava/util/Map;",
        "Ljava/util/List;",
        "Lywl;",
        "Ljava/util/Map;",
        "Ljava/util/Map;",
        "Ljava/util/Set;",
    ),
)

/** 2026.41.3+ RegionsData (obfuscated wpl). */
internal object RegionsDataFingerprint : Fingerprint(
    definingClass = "Lwpl;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    parameters = listOf(
        "Z",
        "Ljava/util/Map;",
        "Ljava/util/List;",
        "Lmcf;",
        "Ljava/util/Map;",
        "Ljava/util/LinkedHashMap;",
        "Ljava/util/Set;",
        "Ls9h;",
    ),
)

/** Pre-2026.41.3 Package.getMAllRegions(). */
internal object PackageAllRegionsFingerprint : Fingerprint(
    definingClass = packageClass,
    name = "d",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf(),
)

/** Pre-2026.41.3 Package.getMActive(). */
internal object PackageActiveFingerprint : Fingerprint(
    definingClass = packageClass,
    name = "c",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf(),
)

/** 2026.41.3+ Package(JSONObject) — fields b=allRegions, c=active (getters removed). */
internal object PackageJsonInitFingerprint : Fingerprint(
    definingClass = packageClass,
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    parameters = listOf("Lorg/json/JSONObject;"),
)

/** Package full ctor — allRegions is p3; active already hardcoded true. */
internal object PackageFullInitFingerprint : Fingerprint(
    definingClass = packageClass,
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    parameters = listOf(
        "J",
        "Z",
        "Z",
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "Z",
        "Z",
        "Lde/komoot/android/services/api/model/Region;",
    ),
)
