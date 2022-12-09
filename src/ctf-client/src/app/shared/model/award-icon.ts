import { IconDefinition } from "@fortawesome/fontawesome-svg-core";
import { faAngry, faBan, faBolt, faBrain, faBug, faCode, faCrosshairs, faCrown, faHatCowboy, faShieldAlt, faSkull } from "@fortawesome/free-solid-svg-icons";

export interface IconMap {
    [key : string] : IconDefinition;
}

export const iconsMap : IconMap = {
    // 'none' : 'none',
    'shield' : faShieldAlt,
    'bug' : faBug,
    'crown' : faCrown,
    'crosshairs' : faCrosshairs,
    'ban' : faBan,
    'lightning' : faBolt,
    'skull' : faSkull,
    'brain' : faBrain,
    'code' : faCode,
    'cowboy': faHatCowboy,
    'angry' : faAngry
}
